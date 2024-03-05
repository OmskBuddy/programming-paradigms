(defn is_vector [x] (and (vector? x) (every? number? x)))
(defn is_matrix [x] (and (vector? x) (every? is_vector x) (apply = (mapv count x))))

(defn tensor_count [x]
      (cond (number? x) 1
            (vector? x) (count x)))

(defn is_tensor [x]
      (cond (number? x) true
            (vector? x) (and (not (= (count x) 0)) (every? is_tensor x) (apply = (mapv tensor_count x)))
            :else false))

(defn shape [x]
      (cond (number? x) [0]
            (vector? (first x)) (conj (shape (first x)) (tensor_count x))
            :else [(tensor_count x)]))

(defn pre-elements-cond [f elements]
      (and
        (or (every? is_vector elements) (every? is_matrix elements))
        (apply = (mapv count elements))
        (or (not (= f /)) (every? #(not (some zero? %)) (rest elements)))
        ))

(defn elements-iter [f elements]
      {:pre  [(pre-elements-cond f elements)]
       :post [(and
                (or (is_vector %) (is_matrix %))
                (= (count %) (count (nth elements 0))))
              ]
       }
      (apply mapv f elements))

(defn v+ [& vectors] (elements-iter + vectors))
(defn v- [& vectors] (elements-iter - vectors))
(defn v* [& vectors] (elements-iter * vectors))
(defn vd [& vectors] (elements-iter / vectors))
(defn v*s [vectors & sc] (mapv #(* % (apply * sc)) vectors))
(defn scalar [& vectors] (apply + (elements-iter * vectors)))
(defn vect [& vectors]
      {:pre [(pre-elements-cond vect vectors)]}
      (reduce
        (fn [vect1 vect2]
            (letfn [(diff [fi se] (- (* (nth vect1 fi) (nth vect2 se)) (* (nth vect1 se) (nth vect2 fi))))]
                   (vector (diff 1 2) (diff 2 0) (diff 0 1))
                   ))
        vectors))
(defn m+ [& matrices] (elements-iter v+ matrices))
(defn m- [& matrices] (elements-iter v- matrices))
(defn m* [& matrices] (elements-iter v* matrices))
(defn md [& matrices] (elements-iter vd matrices))
(defn m*s [matrix & sc]
      {:pre [(every? number? sc)]}
      (vec (map #(v*s % (apply * sc)) matrix)))
(defn m*v [matrices vc] (vec (map #(scalar % vc) matrices)))
(defn transpose [matrix] (apply mapv vector matrix))
(defn m*m [& matrices]
      {:pre [(every? is_matrix matrices)]}
      (reduce
        (fn [matrix1 matrix2] (mapv (fn [x] (mapv (partial scalar x) (transpose matrix2))) matrix1))
        matrices))

(defn tensor-iter [f tensors]
      {:pre [(and (every? is_tensor tensors) (apply = (mapv shape tensors)))]}
      (if (number? (first tensors))
        (apply f tensors)
        (apply mapv #(tensor-iter f %&) tensors)))

(defn t+ [& tensors] (tensor-iter + tensors))
(defn t- [& tensors] (tensor-iter - tensors))
(defn t* [& tensors] (tensor-iter * tensors))
(defn td [& tensors] (tensor-iter / tensors))
