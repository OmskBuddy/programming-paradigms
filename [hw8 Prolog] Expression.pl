:- load_library('alice.tuprolog.lib.DCGLibrary').


lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).


variable(Name, variable(Name)).
const(Value, const(Value)).


op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_negate(A, operation(op_negate, A)).
op_not(A, operation(op_not, A)).
op_and(A, B, operation(op_and, A, B)).
op_or(A, B, operation(op_or, A, B)).
op_xor(A, B, operation(op_xor, A, B)).


operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.
operation(op_not, A, R) :- bool(A, AT), R is 1 - AT.
operation(op_and, A, B, R) :- bool(A, AT), bool(B, BT), R is AT * BT.
operation(op_or, A, B, R) :- bool(A, AT), bool(B, BT), C is AT + BT, bool(C, CT), R is CT.
operation(op_xor, A, B, R) :- bool(A, AT), bool(B, BT), C is AT + BT, R is mod(C, 2).


bool(N, 0.0) :- N =< 0.
bool(N, 1.0) :- N > 0, !.


op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].
op_p(op_not) --> ['!'].
op_p(op_and) --> ['&', '&'].
op_p(op_or) --> ['|', '|'].
op_p(op_xor) --> ['^', '^'].


first([H | T], H).


evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :-
		atom_chars(Name, NameList),
		first(NameList, MainName),
		lookup(MainName, Vars, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).


nonvar(V, T) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).


digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['-', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])},
  [H],
  digits_p(T).


variable_p([]) --> [].
variable_p([H | T]) -->
	{ member(H, ['x', 'y', 'z', 'X', 'Y', 'Z']) },
	[H],
	variable_p(T).


skip --> [].
skip --> [' '], skip.


negation_cond([R | _]) :- R \= '-'.
negation_cond([R, _ | _]) :- R = '-'.


expr_p(variable(Name)) -->
		{ nonvar(Name, atom_chars(Name, Chars)) },
		skip,
		variable_p(Chars),
		skip,
		{ Chars = [_ | _], atom_chars(Name, Chars) }.
expr_p(const(Value)) -->
		{ nonvar(Value, number_chars(Value, Chars)) },
		skip,
		digits_p(Chars),
		skip,
		{ negation_cond(Chars), number_chars(Value, Chars) }.
expr_p(operation(Op, A, B)) -->
		skip, ['('],
		skip, expr_p(A),
		skip, [' '], op_p(Op),
		skip, [' '], expr_p(B),
		skip, [')'], skip.
expr_p(operation(Op, A)) -->
		skip, op_p(Op),
		skip, [' '],
		expr_p(A), skip.


expr_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
expr_str(E, A) :-   atom(A), atom_chars(A, C), phrase(expr_p(E), C).


infix_str(Expr, String) :- expr_str(Expr, String), !.
