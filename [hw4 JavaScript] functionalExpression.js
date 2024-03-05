"use strict";

const letters = {'x' : 0, 'y' : 1, 'z' : 2}
const operator = f => (...ops) => (...args) => f(...ops.map(a => a(...args)))
const variable = x => (...args) => args[letters[x]]
const cnst = x => (...args) => x
const add = (x, y) => operator((a, b) => a + b)(x, y)
const subtract = (x, y) => operator((a, b) => a - b)(x, y)
const multiply = (x, y) => operator((a, b) => a * b)(x, y)
const divide = (x, y) => operator((a, b) => a / b)(x, y)
const negate = x => operator(a => -a)(x)
const madd = (x, y, z) => operator((a, b, c) => a * b + c)(x, y, z)
const floor = x => operator(Math.floor)(x)
const ceil = x => operator(Math.ceil)(x)

const one = cnst(1)
const two = cnst(2)

const operations = {'+' : add, '-' : subtract, '*' : multiply, '/' : divide,
    'negate' : negate, '*+' : madd, '_' : floor, '^' : ceil, 'one' : one, 'two' : two}
const constants = {'one' : one, 'two' : two}

const parse = s => (...args) => s.trim().split(/\s+/).reduce(
    (stack, current) => {
        if (!isNaN(parseFloat(current))) {
            stack.push(cnst(parseFloat(current)))
        } else if (current in letters) {
            stack.push(variable(current))
        } else if (current in constants) {
            stack.push(constants[current])
        } else if (current in operations) {
            let k = operations[current].length
            let argums = stack.splice(stack.length - k, k)
            stack.push(operations[current](...argums))
        }
        return stack
    },
    []
)[0](...args)