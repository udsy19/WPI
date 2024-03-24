;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname Pham-H-Anand-U-hw2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
;Names: Hien Pham, Udaya Tejas Vijay Anand
;Usernames: hpham, uvijayanand

;Question 1
(define-struct tornado (scale distance max-winds))

;; tornado is (make-tornado String Number Number)
;; interpret: represents a tornado where
;;     scale is the Fujita scale rating of a storm
;;     distance is the distance the storm travels in miles
;;     max-winds is the storm's maximum winds
(define tor1(make-tornado "F2" 4 157))
(define tor2(make-tornado "F4" 46 157))
(define tor3(make-tornado "F5" 44 160))

(define-struct hurricane (name category max-winds velocity heading))

;;hurricane is (make-hurricane String Number Number Number String)
;; interpret: represents a hurricane where
;;        name is the name of the hurricane
;;        category is a number between 1 and 5
;;        max-winds is the maximum sustained wind in miles per hour
;;        velocity is the velocity of the storm in miles per hour
;;        heading is the direction of the hurricane
(define hur1(make-hurricane "Katrina" 5 175 30 "SW"))
(define hur2(make-hurricane "Haiyan" 4 175 30 "W"))
(define hur3(make-hurricane "Joaquin" 2 175 30 "N"))

(define-struct thunderstorm (heading velocity rainfall max-winds))

;; thunderstorm is (make-thunderstorm String Number Number)
;; interpret: represents a thunderstorm where
;;     heading is the direction of the thunderstorm
;;     velocity is the velocity of the thunderstorm in miles per hour
;;     rainfall is the number of inches of rainfall
;;     max-winds is the storm's maximum winds
(define thunder1(make-thunderstorm "N" 30 15 215))
(define thunder2(make-thunderstorm "S" 30 4 49))
(define thunder3(make-thunderstorm "E" 30 4 51))
(define thunder4(make-thunderstorm "W" 30 6 49))


;;Data definitions:

;; a windstorm is one of:
;; - tornado
;; - hurricane
;; - thunderstorm

;----------------------------------------------------------------------------------------------------------------------

;Question 2:

;Template for a function that takes a tornado as an argument
; ;; fn-for-tornado: tornado ->
; ;;
;(define (fn-for-tornado tor)
;  (... (tornado-scale tor)
;       (tornado-distance tor)
;       (tornado-max-winds tor)))

;Template for a function that takes a hurricane as an argument
; ;; fn-for-hurricane: hurricane ->
; ;;
;(define (fn-for-hurricane hur)
;  (... (hurricane-name hur)
;       (hurricane-category hur)
;       (hurricane-max-winds hur)
;       (hurricane-velocity hur)
;       (hurricane-heading hur)))

;Template for a function that takes a thunderstorm as an argument
; ;; fn-for-thunderstorm: thunderstorm ->
; ;;
;(define (fn-for-thunderstorm thun)
;  (... (thunderstorm-heading thun)
;       (thunderstorm-category thun)
;       (thunderstorm-max-winds thun)
;       (thunderstorm-velocity thun)))

;Template for a function that takes a windstorm as an argument
; ;; fn-for-windstorm: windstorm ->
; ;;
;(define (fn-for-windstorm c) 
;  (cond [(tornado? c) (...)]
;        [(hurricane? c) (...)]
;        [(thunderstorm? c) (...)]))

;---------------------------------------------------------------------------------------------------------------------

;Question 3

;---------------------------------------------------------------------------------------------------------------------

;Question 4:
(define (violent? wind)
  (cond [(tornado? wind)
         (if (or (equal? (tornado-scale wind) "F4") (equal? (tornado-scale wind) "F5"))
             true
             false)]
        [(hurricane? wind)
         (if (or (= (hurricane-category wind) 4) (= (hurricane-category wind) 5))
             true
             false)]
        [(thunderstorm? wind)
         (if (and (> (thunderstorm-rainfall wind) 5) (> (thunderstorm-max-winds wind) 50))
             true
             false)]))
;; windstorm -> Boolean
;; return true if the windstorm is is a tornado with a Fujita scale rating of "F4" or "F5", a category 4 or 5 hurricane, or a thunderstorm 
;; with more than 5 inches of rainfall and winds exceeding 50 mph.

(check-expect (violent? tor1) false)
;the tornado does not have a Fujita scale rating of "F4" or "F5"
(check-expect (violent? tor2) true)
;the tornado has a Fujita scale rating of "F4"
(check-expect (violent? tor3) true)
;the tornado has a Fujita scale rating of "F5"
(check-expect (violent? hur1) true)
;the hurricane has a category of 5
(check-expect (violent? hur2) true)
;the hurricane has a category of 4
(check-expect (violent? hur3) false)
;the hurricane does not have a category of 4 or 5
(check-expect (violent? thunder1) true)
;the thunderstorm with more than 5 inches of rainfall and winds exceeding 50 mph
(check-expect (violent? thunder2) false)
;the thunderstorm with less or equal than 5 inches of rainfall and winds less or equal than 50 mph
(check-expect (violent? thunder3) false)
;the thunderstorm with less or equal than 5 inches of rainfall
(check-expect (violent? thunder4) false)
;the thunderstorm with winds less or equal than 50mph

;--------------------------------------------------------------------------------------------------------------------

;Question 5
(define (change-max-winds c num)
  (cond [(tornado? c) (make-tornado (tornado-scale c) (tornado-distance c) num)]
        [(hurricane? c) (make-hurricane (hurricane-name c) (hurricane-category c) num (hurricane-velocity c) (hurricane-heading c))]
        [(thunderstorm? c) (make-thunderstorm (thunderstorm-heading c) (thunderstorm-velocity c) (thunderstorm-rainfall c) num)]))
;; windstorm Number -> windstorm
;; produce a windstorm that is the same as the original windstorm except for its max-winds is changed to the given wind speeds

(check-expect (change-max-winds tor1 999) (make-tornado (tornado-scale tor1) (tornado-distance tor1) 999))
;checking if the change is correct for tornado
(check-expect (change-max-winds hur1 500) (make-hurricane (hurricane-name hur1) (hurricane-category hur1) 500 (hurricane-velocity hur1) (hurricane-heading hur1)))
;checking if the change is correct for hurricane
(check-expect (change-max-winds thunder1 120) (make-thunderstorm (thunderstorm-heading thunder1) (thunderstorm-velocity thunder1) (thunderstorm-rainfall thunder1) 120))
;checking if the change is correct for thunderstorm

;--------------------------------------------------------------------------------------------------------------------

;Question 6
(define (acrostic los)
  (cond [(empty? los) ""]
        [(cons? los) (string-append(substring(first los) 0 1) (acrostic (rest los)))]))
;; ListOfString -> String
;; produces a string consisting of just the first character of each string in the ListOfString.

(check-expect (acrostic(cons "fa"(cons "asss" empty))) "fa")
(check-expect (acrostic(cons "haha"(cons "ngu"(cons "good"(cons "anh" empty))))) "hnga")
;2 test cases checking if the function correctly produces a string consisting of just the first character of each string in the ListOfString 

;--------------------------------------------------------------------------------------------------------------------

;Question 7
(define (ickle-strings los)
  (cond [(empty? los) empty]
        [(cons? los)
         (if (string-contains? "ickle" (string-downcase(first los))) ;string-downcase is to make case-insensitive
                  (cons (first los) (ickle-strings (rest los)))
                  (ickle-strings(rest los)))]))
;; ListOfString -> ListOfString
;; produces a ListOfString contains only those strings from the original list that have "ickle" as a substring somewhere in them.

(check-expect (ickle-strings (cons "pig" (cons "pickles" empty))) (cons "pickles" empty))
;checking if the function correctly produces the list of strings that has "ickle" as a substring (all lowercase case)
(check-expect (ickle-strings (cons "ngu" (cons "nickles" (cons "TICklE" (cons "coc" (cons "ICKLES" empty)))))) (cons "nickles"(cons "TICklE"(cons "ICKLES" empty)))) 
;checking if the function correctly produces the list of strings that has "ickle" as a substring (case-insensitive case)

;-------------------------------------------------------------------------------------------------------------------

;Question 8
;ListOfNatural is one of:
; - empty
; - cons(Natural ListOfNatural)
; interpret: a list of Natural

(define (lengths-of-strings los)
  (cond [(empty? los) empty]
        [(cons? los) (cons (string-length (first los))(lengths-of-strings(rest los)))]))
;; ListOfString -> ListOfNatural
;; produces a list of the lengths of each of the strings in the given ListOfString. 

(check-expect (lengths-of-strings (cons "hello"(cons "haha" empty))) (cons 5(cons 4 empty)))
(check-expect (lengths-of-strings (cons "hien"(cons "yeu"(cons "hangnga"(cons "vailon" empty))))) (cons 4(cons 3(cons 7(cons 6 empty)))))
;2 test cases checking if the function correctly produces list of the lengths of each of the strings in the given ListOfString.   