(defn parser [value dict cnst varbl]
      ; :NOTE: (fn [value]
      (letfn [(parse [tokens] (cond (number? tokens) (cnst tokens)
                                    (symbol? tokens) (varbl (str tokens))
                                    :else (apply
                                            (get dict (first tokens))
                                            (mapv parse (rest tokens)))))]
             (parse (read-string value))))

(defn op [f]
      (fn [& args]
          (fn [display]
              (apply f (mapv #(% display) args)))))

(def constant (fn [cnst] (fn [display] (double cnst))))
(def variable (fn [var] (fn [display] (double (get display var)))))
(def negate
  (fn [value]
      (fn [display]
          (- (value display)))))
(def add (op +))
(def subtract (op -))
(def multiply (op *))
(def div-eval (fn [& args] (reduce #(/ (double %1) (double %2)) (if (empty? (rest args))
                                                                  (conj args 1.0) args))))
(def divide (op div-eval))
(def sumexp (op (fn [& args] (apply + (mapv (fn [x] (Math/exp x)) args)))))
(def lse (op (fn [& args]
                 (Math/log
                   (apply + (mapv (fn [x] (Math/exp x)) args))))))

(def operations {
                 '+ add
                 '- subtract
                 '* multiply
                 '/ divide
                 'negate negate
                 'sumexp sumexp
                 'lse lse
                 })

(defn parseFunction [value] (parser value operations constant variable))

(load-file "proto.clj")
(def __sign (field :sign))
(def __values (field :values))
(def __func (field :func))
(def __dfunc (field :dfunc))

(def _toString (method :toString))
(def _toStringInfix (method :toStringInfix))
(def _evaluate (method :evaluate))
(def _diff (method :diff))

(declare Multiply)
(declare Divide)
(declare Constant)
(declare diff)
(declare evaluate)
(declare toString)
(declare toStringInfix)

; :NOTE: memory (prototypes)
(defn Constant [x]
      {
       :values x
       :toString (fn [this] (str (__values this)))
       :toStringInfix (fn [this] (str (__values this)))
       :diff (fn [this var] (Constant 0))
       :evaluate (fn [this vars] (__values this))
       })

(def ZERO (Constant 0))
(def ONE (Constant 1))

(defn Variable [x]
      {
       :values x
       :toString (fn [this] (__values this))
       :toStringInfix (fn [this] (__values this))
       :diff (fn [this var] (if (= (__values this) var) ONE ZERO))
       :evaluate (fn [this vars] (get vars (__values this)))
       })

(defn Negate [expression]
      {
       :values expression
       :toString (fn [this] (str "(negate " (toString (__values this)) ")"))
       :toStringInfix (fn [this] (str "negate " (toStringInfix (__values this))))
       :diff (fn [this var] (Negate (diff (__values this) var)))
       :evaluate (fn [this vars] (- (evaluate (__values this) vars)))
       })

(defn PolyOperationConstructor
      [sign func dfunc ctor prototype]
      (let [proto {:prototype prototype
                   :sign      sign
                   :func      func
                   :dfunc     dfunc}]
           (fn [& args] (apply ctor proto args))))

(def PolyOperationPrototype
  {
   :toString (fn [expression] (str
                                "("
                                (__sign expression)
                                " "
                                (clojure.string/join " " (mapv #(toString %) (__values expression)))
                                ")"))
   :toStringInfix (fn [expression] (str
                                     "("
                                     (clojure.string/join
                                       (str " " (__sign expression) " ")
                                       (mapv #(toStringInfix %) (__values expression)))
                                     ")"))
   :diff (fn [expression var]
             ((__dfunc expression) (__values expression) var))
   :evaluate (fn [expression vars]
                 (apply (__func expression) (mapv #(evaluate % vars) (__values expression))))
   })

(defn PolyOperation [this & expressions]
      (assoc this
             :values expressions))

(defn PolyOperationFactory [sign func dfunc]
      (PolyOperationConstructor sign func dfunc PolyOperation PolyOperationPrototype))

(def Add (PolyOperationFactory
           "+"
           +
           (fn [expressions var] (apply Add (mapv #(diff % var) expressions)))))

(def Subtract (PolyOperationFactory
                "-"
                -
                ; :NOTE: explicit diff
                (fn [expressions var] (apply Subtract (mapv #(diff % var) expressions)))))

(defn mult-diff [expressions var]
      ; :NOTE: if-arity
      (if (= (count expressions) 1)
        (diff (first expressions) var)
        ; :NOTE: explicit recursion
        (Add
          (apply Multiply (diff (first expressions) var) (rest expressions))
          (Multiply (first expressions) (mult-diff (rest expressions) var)))))
(def Multiply (PolyOperationFactory
                "*"
                *
                mult-diff))

(defn div-diff [expressions var]
      (if (= (count expressions) 1)
        (diff (Divide ONE (first expressions)) var)
        (Divide
          (Subtract
            (apply Multiply (diff (first expressions) var) (rest expressions))
            (Multiply (first expressions) (diff (apply Multiply (rest expressions)) var)))
          (Multiply (apply Multiply (rest expressions)) (apply Multiply (rest expressions))))))
(def Divide (PolyOperationFactory "/" div-eval div-diff))

(defn meansq-eval [& args] (/ (apply + (mapv #(* % %) args)) (count args)))

(def Meansq (PolyOperationFactory
              "meansq"
              meansq-eval
              ; :NOTE: to closed form
              (fn [expressions var] (diff
                                      (Divide
                                        (apply Add (mapv #(Multiply % %) expressions))
                                        (Constant (count expressions)))
                                      var))))

(def Square (PolyOperationFactory
              "square"
              (fn [arg] (Math/sqrt arg))
              (fn [expressions var]
                  (Divide
                    (diff (first expressions) var)
                    (Multiply (Constant 2) (Square (first expressions)))))))

(def RMS (PolyOperationFactory
           "rms"
           (fn [& args] (Math/sqrt (apply meansq-eval args)))
           (fn [expressions var] (diff
                                   (Square (apply Meansq expressions))
                                   var))))

(def objects
  {
   '+ Add
   '- Subtract
   '* Multiply
   '/ Divide
   'negate Negate
   'meansq Meansq
   'rms RMS
   })

(def ops ["+" "-" "/" "*"])
(def unary ["negate" "!"])

(def max-level 2)

(defn evaluate [expression vars] (_evaluate expression vars))
(defn toString [expression] (_toString expression))
(defn diff [expression var] (_diff expression var))

(defn parseObject [value] (parser value objects Constant Variable))
