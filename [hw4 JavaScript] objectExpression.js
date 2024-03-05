const letters = {
    'x': 0,
    'y': 1,
    'z': 2,
}

const reverseString = s => {
    return s.split('').reverse().join('')
}

function Operator(...ops) {
    this.getOps = function () {
        return ops
    }
}

function Expression(sgn, f, df, ...ops) {
    Operator.call(this, ...ops)

    this.toString = function () {
        return this.getOps().join(' ') + ' ' + sgn
    }
    this.evaluate = function (...vars) {
        return f(...ops.map(x => x.evaluate(...vars)))
    }
    this.diff = function (ch) {
        return df(...ops)(ch)
    }
    this.prefix = function () {
        return '(' + sgn + ' ' + this.getOps().map(x => x.prefix()).join(' ') + ')'
    }
    this.postfix = function () {
        return '(' + this.getOps().map(x => x.postfix()).join(' ') + ' ' + sgn + ')'
    }
}
Expression.prototype = Object.create(Operator.prototype)

function Variable(ch) {
    this.getCh = function () { return ch }
    this.toString = function () {
        return ch
    }
    this.evaluate = function (...vars) {
        return vars[letters[this.getCh()]]
    }
    this.diff = function (ch) {
        return ch === this.getCh() ? new Const(1) : new Const(0)
    }
    this.prefix = function () {
        return this.toString()
    }
    this.postfix = function () {
        return this.toString()
    }
}

function Const(x) {
    this.getX = function () { return x }
    this.toString = function () {
        return x.toString()
    }
    this.evaluate = function (...vars) {
        return x
    }
    this.diff = function (ch) {
        return new Const(0)
    }
    this.prefix = function () {
        return this.toString()
    }
    this.postfix = function () {
        return this.toString()
    }
}

function Add(x, y) {
    Expression.call(this, '+', (a, b) => a + b, (a, b) => (ch) => new Add(a.diff(ch), b.diff(ch)), x, y)
}
Add.prototype = Object.create(Expression.prototype)

function Subtract(x, y) {
    Expression.call(this, '-', (a, b) => a - b, (a, b) => (ch) => new Subtract(a.diff(ch), b.diff(ch)), x, y)
}
Subtract.prototype = Object.create(Expression.prototype)

function Multiply(x, y) {
    Expression.call(this, '*', (a, b) => a * b, (a, b) => ch => new Add(new Multiply(a.diff(ch), b), new Multiply(a, b.diff(ch))), x, y)
}
Multiply.prototype = Object.create(Expression.prototype)

function Divide(x, y) {
    Expression.call(
        this,
        '/',
        (a, b) => a / b,
        (a, b) => ch => new Divide(
            new Subtract(
                new Multiply(a.diff(ch), b),
                new Multiply(a, b.diff(ch))
                ),
            new Multiply(b, b)
            ),
        x, y
        )
}
Divide.prototype = Object.create(Expression.prototype)


function Negate(x) {
    Expression.call(this, 'negate', a => -a, a => ch => new Negate(a.diff(ch)), x)
}
Negate.prototype = Object.create(Expression.prototype)

function Sqrt(x) {
    Expression.call(this, 'sqrt', a => Math.sqrt(a),
        a => ch => new Multiply(new Divide(new Const(1), new Multiply(new Const(2), new Sqrt(x))), a.diff(ch)), x)
}

const diffOperator = (start, next) => (...arr) => {
    return arr.slice(1).reduce(
        (total, current) => {
            total = next(total, current)
            return total
        },
        start(arr[0])
        )
}

function Sumsq(...ops) {
    Expression.call(
        this,
        'sumsq' + ops.length.toString(),
        (...args) => diffOperator(a => a * a, (a, b) => a + b * b)(...args),
        (...args) => ch => diffOperator(a => a.diff(ch), (a, b) => new Add(a, b.diff(ch)))(...args.map(x => new Multiply(x, x))),
        ...ops
        )
}
Sumsq.prototype = Object.create(Expression.prototype)

function Sumsq2(x1, x2) {
    Sumsq.call(this, x1, x2)
}
Sumsq2.prototype = Object.create(Sumsq.prototype)

function Sumsq3(x1, x2, x3) {
    Sumsq.call(this, x1, x2, x3)
}
Sumsq3.prototype = Object.create(Sumsq.prototype)

function Sumsq4(x1, x2, x3, x4) {
    Sumsq.call(this, x1, x2, x3, x4)
}
Sumsq4.prototype = Object.create(Sumsq.prototype)

function Sumsq5(x1, x2, x3, x4, x5) {
    Sumsq.call(this, x1, x2, x3, x4, x5)
}
Sumsq5.prototype = Object.create(Sumsq.prototype)

function Distance(...ops) {
    Expression.call(
        this,
        'distance' + ops.length.toString(),
        (...args) => Math.sqrt(diffOperator(a => a * a, (a, b) => a + b * b)(...args)),
        (...args) => ch => new Sqrt(new Sumsq(...args)).diff(ch),
        ...ops
        )
}
Distance.prototype = Object.create(Expression.prototype)

function Distance2(x1, x2) {
    Distance.call(this, x1, x2)
}
Distance2.prototype = Object.create(Distance.prototype)

function Distance3(x1, x2, x3) {
    Distance.call(this, x1, x2, x3)
}
Distance3.prototype = Object.create(Distance.prototype)

function Distance4(x1, x2, x3, x4) {
    Distance.call(this, x1, x2, x3, x4)
}
Distance4.prototype = Object.create(Distance.prototype)

function Distance5(x1, x2, x3, x4, x5) {
    Distance.call(this, x1, x2, x3, x4, x5)
}
Distance5.prototype = Object.create(Distance.prototype)

function Exp(x) {
    Expression.call(this, 'exp', x => Math.exp(x), x => ch => new Multiply(new Exp(x), x.diff(ch)), x)
}
Exp.prototype = Object.create(Expression.prototype)

function Sumexp(...ops) {
    Expression.call(
        this,
        'sumexp',
        (...args) => args.length === 0 ? 0 : diffOperator(x => Math.exp(x), (a, b) => a + Math.exp(b))(...args),
        (...args) => ch => diffOperator(x => x.diff(ch), (a, b) => new Add(a, b.diff(ch)))(...args.map(x => new Exp(x))),
        ...ops
        )
}
Sumexp.prototype = Object.create(Expression.prototype)

function LSE(...ops) {
    Expression.call(
        this,
        'lse',
        (...args) => args.length === 0 ? 0 : Math.log(diffOperator(x => Math.exp(x), (a, b) => a + Math.exp(b))(...args)),
        (...args) => ch => new Multiply(new Divide(new Const(1), new Sumexp(...args)), new Sumexp(...args).diff(ch)),
        ...ops
        )
}
LSE.prototype = Object.create(Expression.prototype)

const operations = {
    '+': Add,
    '-': Subtract,
    '*': Multiply,
    '/': Divide,
    'negate': Negate,
    'sumsq2': Sumsq2,
    'sumsq3': Sumsq3,
    'sumsq4': Sumsq4,
    'sumsq5': Sumsq5,
    'distance2': Distance2,
    'distance3': Distance3,
    'distance4': Distance4,
    'distance5': Distance5,
    'sumexp': Sumexp,
    'lse': LSE
}

const parse = s => s.trim().split(/\s+/).reduce(
    (stack, current) => {
        if (!isNaN(parseFloat(current))) {
            stack.push(new Const(parseFloat(current)))
        } else if (current in letters) {
            stack.push(new Variable(current))
        } else if (current in operations) {
            let k = operations[current].length
            let argums = stack.splice(stack.length - k, k)
            stack.push(new operations[current](...argums))
        }
        return stack
    },
    []
    )[0]

function ParseError(message) {
    Error.call(this, message)
    this.message = message
}
ParseError.prototype = Object.create(Error.prototype)
ParseError.prototype.name = 'ParseError'

function StringSource(s, start, limit, turn) {
    this.str = " " + s + " "
    this.pos = start(this.str)

    this.next = function () {
        this.pos = turn(this.pos, 1)
        return this.str[this.pos]
    }
    this.hasNext = function () {
        return limit(this.str, turn(this.pos, 1))
    }
    this.back = function (cnt) {
        this.pos = turn(this.pos, -cnt)
    }
    this.get = function () {
        return this.str[this.pos]
    }
    this.check = function(symbol) {
        return !RegExp(/\s/).test(symbol) && symbol !== '(' && symbol !== ')'
    }
}

function ParserSource (s, start, limit, turn) {
    this.source = new StringSource(s, start, limit, turn)
    this.END = '\0'
    this.ch = this.source.hasNext() ? this.source.next() : this.END

    this.take = function () {
        let result = this.ch
        this.ch = this.source.hasNext() ? this.source.next() : this.END
        return result
    }
    this.test = function (ch) {
        if (ch instanceof RegExp) {
            return ch.test(this.ch)
        }
        return ch === this.ch
    }
    this.takeSym = function (ch) {
        if (this.test(ch)) {
            this.take()
            return true
        }
        return false
    }
    this.takeWord = function () {
        let result = []
        let current = this.take()

        while (current !== this.END && this.source.check(current)) {
            result.push(current)
            current = this.take()
        }

        this.back(1)
        return result.join('')
    }
    this.eof = function () {
        this.skipWs()
        return this.ch === this.END
    }
    this.skipWs = function () {
        while (RegExp(/\s/).test(this.ch)) {
            this.take()
        }
    }
    this.expect = function (ch) {
        if (!this.takeSym(ch)) {
            throw new ParseError("expected '" + ch + "', found '" + this.ch + "'")
        }
    }
    this.back = function (cnt) {
        this.source.back(cnt)
        this.ch = this.source.get()
    }
}

function ParserPrefix(s, start, limit, turn, openBracket, closeBracket, normalize, normalizeArg) {
    ParserSource.call(this, s, start, limit, turn)

    this.getExpr = function () {
        let result = this.getRecursive()
        if (this.eof()) {
            return result
        }
        throw new ParseError("expected end of expression")
    }
    this.getRecursive = function () {
        this.skipWs()
        if (this.takeSym(openBracket)) {
            let op = this.getOp()
            let k = op.length
            let argums = []

            while (true) {
                this.skipWs()
                if (this.takeSym(closeBracket)) {
                    this.back(closeBracket.length)
                    break
                }
                argums.push(this.getRecursive())
            }

            if (k === 0 || k > 0 && k === argums.length) {
                this.skipWs()
                this.expect(closeBracket)
                return new op(...normalizeArg(argums))
            } else {
                throw new ParseError("unexpected count of arguments: " + normalizeArg(argums))
            }
        } else {
            let current = normalize(this.takeWord())
            if (isVariable(current)) {
                if (!this.test(')') && !this.test('(')) {
                    this.take()
                }
                return new Variable(current)
            } else if (isConst(current)) {
                if (!this.test(')') && !this.test('(')) {
                    this.take()
                }
                return new Const(parseFloat(current))
            } else {
                throw new ParseError("unknown token: " + current)
            }
        }

    }
    this.getOp = function () {
        this.skipWs()
        let current = normalize(this.takeWord())
        if (current in operations) {
            return operations[current]
        } else {
            throw new ParseError("unknown operation: " + current)
        }
    }
}
ParserPrefix.prototype = Object.create(ParserSource.prototype)

const parsePrefix = s => {
    let parser = new ParserPrefix(
        s,
        s => -1,
        (str, x) => x < str.length,
        (x, k) => x + k,
        '(',
        ')',
        x => x,
        x => x
        )
    return parser.getExpr()
}

const parsePostfix = s => {
    let parser = new ParserPrefix(
        s,
        s => s.length,
        (str, x) => -1 < x,
        (x, k) => x - k,
        ')',
        '(',
        x => reverseString(x),
        x => x.reverse()
        )
    return parser.getExpr()
}

function isVariable(str) {
    return (str in letters);
}

function isConst(str) {
    return !isNaN(str) && !isNaN(parseInt(str))
}
