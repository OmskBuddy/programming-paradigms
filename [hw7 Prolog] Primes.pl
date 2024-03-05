composite(0).
composite(1).
prime(X) :- \+ composite(X).

min_div(Prime, Prime) :- prime(Prime), !. % :NOTE: memoize

add_div(Num, Prime) :- min_div(Num, _), !.
add_div(Num, Prime) :- assert(min_div(Num, Prime)).

add_comp(Num) :- composite(Num), !.
add_comp(Num) :- assert(composite(Num)).


pow(_, 0, 1) :- !.
pow(Num, Base, Result) :- Base > 0, 1 is mod(Base, 2),
						  Base1 is Base - 1, pow(Num, Base1, Result1),
						  Result is Num * Result1.
pow(Num, Base, Result) :- Base > 0, 0 is mod(Base, 2),
						  Base2 is div(Base, 2), pow(Num, Base2, Result2),
						  Result is Result2 * Result2.

logarithm(1, _, 0) :- !.
logarithm(Num, Base, 0) :- Base > 1, \+ (0 is mod(Num, Base)).
logarithm(Num, Base, Result) :- Base > 1, 0 is mod(Num, Base), Num1 is div(Num, Base),
								logarithm(Num1, Base, Result1), Result is Result1 + 1.


int_build(Prime, Num, MAX) :- Num =< MAX,
							  add_comp(Num),
							  add_div(Num, Prime),
							  NewNum is Num + Prime,
							  int_build(Prime, NewNum, MAX).

ext_build(Num, MAX) :- Num =< MAX,
					   prime(Num),
				       Num2 is Num * Num,
					   int_build(Num, Num2, MAX),
					   Num1 is Num + 1,
					   ext_build(Num1, MAX).

ext_build(Num, MAX) :- Num =< MAX,
					   Num1 is Num + 1,
					   ext_build(Num1, MAX).


init(MAX_N) :- ext_build(2, MAX_N), !.


count_pow(Num, 0, []) :- !.
count_pow(Num, Count, [Num | T]) :- count_pow(Num, Count1, T), Count is Count1 + 1, !.
count_pow(Num, Count, [Num1 | T]) :- Count is 0.

remove_left(List, N, Result) :- length(Prefix, N), append(Prefix, Result, List).
    
pack([], []) :- !.
pack([Num | T], [(Num, Count) | RT]) :- count_pow(Num, Count1, T), remove_left(T, Count1, T1), Count is Count1 + 1, pack(T1, RT).


append_num(Num, 0, List, List) :- !.
append_num(Num, Pow, OldList, [Num | NewList]) :- Pow1 is Pow - 1, append_num(Num, Pow1, OldList, NewList).

unpack([], []) :- !. 
unpack([(Num, Pow) | T], [Num | RT]) :- Pow1 is Pow - 1, append_num(Num, Pow1, NewRT, RT), unpack(T, NewRT).

% fixed (cpd is used by pd)
cpd_helper(1, _, []) :- !.
cpd_helper(Num, Last, [(H1, H2) | T]) :- number(Num),
										 min_div(Num, H1),
										 logarithm(Num, H1, H2),
										 pow(H1, H2, H),
										 Num1 is div(Num, H),
										 cpd_helper(Num1, H1, T).

cpd_helper(Num, Last, [(H1, H2) | T]) :- \+ number(Num),
										 Last =< H1,
										 pow(H1, H2, H),
										 cpd_helper(Num1, H1, T),
										 Num is Num1 * H.


compact_prime_divisors(Num, List) :- cpd_helper(Num, 1, List), !.

prime_divisors(Num, List) :- number(Num), compact_prime_divisors(Num, List1), unpack(List1, List), !.
prime_divisors(Num, List) :- pack(List, List1), compact_prime_divisors(Num, List1).
