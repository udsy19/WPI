;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname Pham-H-Anand-U-hw5) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
;Names: Hien Pham, Udaya Tejas Vijay Anand
;Usernames: hpham, uvijayanand

;Question 1
(define-struct river(name ph bloom? tributaries))
;; a river is (make-river String Number Boolean ListOfRiver)
;; interpret: represents a river where
;;     name is the name of the river
;;     ph is the pH of the water
;;     bloom? is the presence of algal blooms
;;     tributaries is a list of rivers that feed into the river

;; a ListOfRiver is one of
;;    empty
;;    (cons river ListOfRiver)

;Question 2

(define riv1(make-river "Nile" 7.0 false empty))
(define riv2(make-river "To Lich" 3.5 false empty))
(define riv4(make-river "Amazon" 8.0 false empty))
(define riv5(make-river "Da" 7.9 true (list riv4)))
(define riv7(make-river "Tien" 12 true empty))
(define riv8(make-river "Hau" 10 false empty))
(define riv3(make-river "Hong" 5.9 false (list riv1 riv2 riv8)))
(define riv6(make-river "Duong" 13.5 false (list riv3 riv5 riv7)))

;Question 3
;Template for a function that takes a river as an argument

; ;; river-fcn:  river ->
; ;;
; (define (river-fcn a-river)
;   (... (river-name a-river)
;        (river-ph a-river)
;        (river-bloom? a-river)
;        (list-of-river-fcn (river-tributaries a-river)))); 
; 

;Template for a function that takes a ListOfRiver as an argument
; (define (list-of-river-fcn alor)
;   (cond [(empty? alor)  (...) ]
;         [(cons? alor)   (...     (river-fcn (first alor))
;                                  (list-of-river-fcn (rest alor)))]))

;Question 4
(define (list-alkaline-rivers riv)
  (if (>= (river-ph riv) 9.5)
      (append (list (river-name riv)) (list-of-river-alkaline (river-tributaries riv)))
      (list-of-river-alkaline (river-tributaries riv))))
;; river -> ListOfString
;; returns a list of the names of rivers in the system that have a pH level of 9.5 or greater.

(define (list-of-river-alkaline lor)
  (cond [(empty? lor) (list )]
        [else
         (append (list-alkaline-rivers (first lor)) (list-of-river-alkaline (rest lor)))]))

;; ListOfRiver -> ListOfString
;; return a list of the names of rivers in the list of tributaries that have a pH level of 9.5 or greater.

(check-expect(list-alkaline-rivers riv6) (list "Duong" "Hau" "Tien"))
(check-expect(list-alkaline-rivers riv5) (list))
(check-expect(list-alkaline-rivers riv3) (list "Hau"))

(check-expect(list-of-river-alkaline (river-tributaries riv3)) (list "Hau"))
(check-expect(list-of-river-alkaline (river-tributaries riv6)) (list "Hau" "Tien"))

;Question 5
(define (algae-free? riv)
  (if (equal? (river-bloom? riv) true)
      false
      (lor-algae-free? (river-tributaries riv))))

;; river -> Boolean
;;  returns true if no river in the system has an algal bloom, else produce false 
(define (lor-algae-free? lor)
  (cond [(empty? lor) true]
        [else
         (if (equal? (algae-free? (first lor)) true)
             (lor-algae-free? (rest lor))
             false)]))
;; ListOfRiver -> Boolean
;; returns true if no river in the list has an algal bloom, else produce false  
(check-expect (algae-free? riv6) false)
(check-expect (algae-free? riv3) true)

(check-expect (lor-algae-free? (river-tributaries riv3)) true)
(check-expect (lor-algae-free? (river-tributaries riv6)) false)

;Question 6
(define (raise-all-ph riv)
  (make-river (river-name riv)
              (+ 0.5 (river-ph riv))
              (river-bloom? riv)
              (lor-raise-all-ph (river-tributaries riv))))
;; river -> river
;; returns a system where pH values of all the rivers in the system have been raised by 0.5. 

(define (lor-raise-all-ph lor)
  (cond [(empty? lor) empty]
        [else
         (cons (raise-all-ph (first lor)) (lor-raise-all-ph (rest lor)))]))
;; ListOfRiver -> ListOfRiver
;; returns a list of river where all pH values have been raised by 0.5 


(check-expect (raise-all-ph riv6) (make-river "Duong" 14 false
                                              (list
                                               (make-river "Hong" 6.4 false (list (make-river "Nile" 7.5 false empty) (make-river "To Lich" 4 false empty) (make-river "Hau" 10.5 false empty)))
                                               (make-river "Da" 8.4 true (list (make-river "Amazon" 8.5 false empty))) (make-river "Tien" 12.5 true empty))))

(check-expect (raise-all-ph riv3) (make-river "Hong" 6.4 false (list
                                                              (make-river "Nile" 7.5 false empty)
                                                              (make-river "To Lich" 4 false empty)
                                                              (make-river "Hau" 10.5 false empty))))

(check-expect (lor-raise-all-ph (river-tributaries riv6)) (list (make-river "Hong" 6.4 false
                                                            (list
                                                             (make-river "Nile" 7.5 false empty)
                                                             (make-river "To Lich" 4 false empty)
                                                             (make-river "Hau" 10.5 false empty)))
                                                             (make-river "Da" 8.4 true (list
                                                                                      (make-river "Amazon" 8.5 false empty)))
                                                                                      (make-river "Tien" 12.5 true empty)))
(check-expect (lor-raise-all-ph (river-tributaries riv3)) (list
                                                           (make-river "Nile" 7.5 false empty)
                                                           (make-river "To Lich" 4 false empty)
                                                           (make-river "Hau" 10.5 false empty)))

;Question 7
(define (find-subsystem name riv)
  (if (equal? (river-name riv) name)
      riv
      (lor-find-subsystem name (river-tributaries riv))))
;; String river -> river or False
;; returns the portion of the original river system that has the named river as its root. If there is no river in the system with the given name, the function returns 
;; false.  

(define (lor-find-subsystem name lor)
  (cond [(empty? lor) false]
        [else
         (if (river? (find-subsystem name (first lor)))
             (find-subsystem name (first lor))
             (lor-find-subsystem name (rest lor)))]))
;; String ListOfRiver -> river or False
;; returns the portion that has the name as its root, If there is no river with the given name, return false

(check-expect (find-subsystem "Hong" riv6) riv3)
(check-expect (find-subsystem "Lmao" riv6) false)

(check-expect (lor-find-subsystem "Hihi" (river-tributaries riv6)) false)
(check-expect (lor-find-subsystem "Tien" (river-tributaries riv6)) riv7)

;Question 8
(define-struct merchandise (name kind autographed? quantity price))
;; a Merchandise is a (make-merchandise String String Boolean Natural Number)
;; interp:
;;        Merchandise represents an item sold at a pop culture emporium, where
;;        name is the name of the merchandise item
;;        kind indicates whether the item is an action figure, board game, costume, manga/comic book, trading card, etc.
;;        autographed?  is true if the item is autographed
;;        quantity is the number of that item that is being purchased
;;        price is the cost of a single item
;; a Receipt (ListOfMerchandise) is one of
;; empty, or
;; (cons Merchandise Receipt)
(define mer1(make-merchandise "Hien" "board game" true 2 5))
(define mer2(make-merchandise "yeu" "love novel" false 50 7))
(define mer3(make-merchandise "Nga" "board game" true 1 60))

(define rec1(cons mer1(cons mer2(cons mer3 empty))))
(define rec2(cons mer3(cons (make-merchandise "cute" "costume" false 50000 30)(cons (make-merchandise "vailon" "trading card" true 50 4) empty))))
(define rec3(cons mer1(cons mer2(cons (make-merchandise "hnga" "trading card" true 5000 300) (cons (make-merchandise "vailon" "trading card" true 50 5) empty)))))
(define rec4(cons mer1(cons (make-merchandise "ngaiu" "board game" false 300 400) empty)))
(define rec5(cons mer1(cons mer3(cons (make-merchandise "iu nhau" "costume" true 1 500000) empty))))


(define (bargain-items lom)
  (local [(define (below? x)
            (< (merchandise-price x) 10))
          (define (names x)
            (merchandise-name x))]
    (map names (filter below? lom))))
;; bargain-items:  ListOfMerchandise -> ListOfString
;; consumes a list of merchandise items and produces a list of the names of all the items with prices under $10.

(check-expect (bargain-items rec1) (list "Hien" "yeu"))
(check-expect (bargain-items rec2) (list "vailon"))
(check-expect (bargain-items rec3) (list "Hien" "yeu" "vailon"))

;Question 9
(define (any-of-kind? lom string)
  (local [(define (kind? x)
            (string=? (merchandise-kind x) string))]
    (not (empty? (filter kind? lom )))))
;; any-of-kind?:  ListOfMerchandise String -> Boolean
;; consumes a ListOfMerchandise and a kind of merchandise item produces true if there is an item of that kind in the ListOfMerchandise

(check-expect (any-of-kind? rec1 "costume") false)
(check-expect (any-of-kind? rec2 "costume") true)
(check-expect (any-of-kind? rec3 "trading card") true)

;Question 10
(define (list-cheap-autograph lom num)
  (local [(define (most? x)
            (and (merchandise-autographed? x) (<= (merchandise-price x) num)))]
    (filter most? lom)))
;; list-cheap-autograph:  ListOfMerchandise Number -> ListOfMerchandise
;; consumes a list of merchandise items and returns a list of those autographed items that cost at most the given amount

(check-expect (list-cheap-autograph rec1 500) (list mer1 mer3))
(check-expect (list-cheap-autograph rec3 200) (list mer1 (make-merchandise "vailon" "trading card" true 50 5)))

  
  