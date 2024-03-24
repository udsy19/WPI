;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname Pham-H-Anand-U-hw3) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
;Names: Hien Pham, Udaya Tejas Vijay Anand
;Usernames: hpham, uvijayanand

;Question 1
(define-struct merchandise(name kind autographed? quantity price))
;; Merchandise is (make-merchandise String String Boolean Natural Number)
;; interpret: represents a Merchandise where
;;     name is the name of the Merchandise
;;     kind is the kind of the Merchandise 
;;     autographed? shows that the Merchandise is autographed or not
;;     quantity is the number of Merchandise
;;     price is the price of a single Merchandise
(define mer1(make-merchandise "Hien" "board game" true 2 40000))
(define mer2(make-merchandise "yeu" "love novel" false 50 50))
(define mer3(make-merchandise "Nga" "board game" true 1 60000))


;Question 2
;Template for a function that takes a merchandise as an argument

; ;; merchandise-fcn:  Merchandise ->
; ;;
; (define (merchandise-fcn a-merchandise)
;   (... (merchandise-name a-merchandise)
;        (merchandise-kind a-merchandise)
;        (merchandise-autographed? a-merchandise)
;        (merchandise-quantity a-merchandise)
;        (merchandise-price a-merchandise)))

;Question 3
;; a Receipt is one of
;;    empty
;;    (cons Merchandise Receipt)

(define rec1(cons mer1(cons mer2(cons mer3 empty))))
(define rec2(cons mer3(cons (make-merchandise "cute" "costume" false 50000 30)(cons (make-merchandise "vailon" "trading card" true 50 50) empty))))
(define rec3(cons mer1(cons mer2(cons (make-merchandise "hnga" "trading card" true 5000 300) (cons (make-merchandise "vailon" "trading card" true 50 50) empty)))))
(define rec4(cons mer1(cons (make-merchandise "ngaiu" "board game" false 300 400) empty)))
(define rec5(cons mer1(cons mer3(cons (make-merchandise "iu nhau" "costume" true 1 500000) empty))))

;Question 4
; ;; Receipt-fcn:  ListOfMerchandise ->
; ;;
; (define (receipt-fcn arec)
;   (cond [(empty? arec)  (...) ]
;         [(cons? arec)   (...     (merchandise-fcn (first arec))
;                                  (rec-fcn (rest arec)))]))

;Question 5

(define (auto? mer)
  (if (equal? (merchandise-autographed? mer) true)
      true
      false))
;; auto?: Merchandise -> Boolean
;; helper function to check if a merchandise is autographed or not
(check-expect(auto? mer1) true)
(check-expect(auto? mer2) false)

(define (list-cheap-autograph r c)
  (cond [(empty? r) empty]
        [(cons? r)
         (if (and (auto? (first r)) (<= (merchandise-price(first r)) c))
             (cons (first r) (list-cheap-autograph (rest r) c))
             (list-cheap-autograph (rest r) c))]))
;; list-cheap-autograph: Receipt Number -> Receipt
;; produce items from the original receipt that are autographed and that cost no more than the number given
(check-expect(list-cheap-autograph rec1 50000) (cons mer1 empty))
(check-expect(list-cheap-autograph rec2 90000) (cons mer3(cons (make-merchandise "vailon" "trading card" true 50 50) empty)))

;Question 6
(define (cards? mer)
  (if (equal? (merchandise-kind mer) "trading card")
      true
      false))
;; card?: Merchandise -> Boolean
;; helper function to check if a merchandise is of type "trading card" or not
(check-expect(cards? mer1) false)
(check-expect(cards? (make-merchandise "vailon" "trading card" true 50 50)) true)

(define (count-trading-cards r)
  (cond [(empty? r) 0]
        [(cons? r)
         (if (cards? (first r))
             (+ (merchandise-quantity (first r)) (count-trading-cards (rest r)))
             (count-trading-cards (rest r)))]))
;; count-trading-cards: Receipt -> Number
;; produces the total number of items in the order that are trading cards
(check-expect(count-trading-cards rec2) 50)
(check-expect(count-trading-cards rec3) 5050)

;Question 7

(define (value mer)
  (* (merchandise-quantity mer) (merchandise-price mer)))
(check-expect (value mer1) 80000)
;;value: Merchandise -> Number
;; helper function to calculate the value of a merchandise
(check-expect (value mer1) 80000)
(check-expect (value mer2) 2500)

(define (receipt-total r)
  (cond [(empty? r) 0]
        [(cons? r)
         (+ (value (first r)) (receipt-total (rest r)))]))
;; receipt-total: Receipt -> Number
;; produces the total cost of all the merchandise items
(check-expect (receipt-total rec1) 142500)
(check-expect (receipt-total rec3) 1585000)

;Question 8

(define (board-game? mer)
  (if (equal? (merchandise-kind mer) "board game")
      true
      false))
;; board-game?: Merchandise -> Boolean
;; helper function to check if a merchandise is of type "board game" or not
(check-expect (board-game? mer1) true)
(check-expect (board-game? mer2) false)

(define (board-games-cost r)
  (cond [(empty? r) 0]
        [(cons? r)
         (if (board-game? (first r))
             (+ (value(first r)) (board-games-cost (rest r)))
             (board-games-cost (rest r)))]))
;; board-games-cost: Receipt -> Number
;; calculates the total cost of all the board games contained in the receipt
(check-expect(board-games-cost rec1) 140000)
(check-expect(board-games-cost rec4) 200000)


;Question 9

(define (costume? mer)
  (if (equal? (merchandise-kind mer) "costume")
      true
      false))
;; costume?: Merchandise -> Boolean
;; helper function to check if a merchandise is of type "costume" or not
(check-expect (costume? mer1) false)
(check-expect (costume? (make-merchandise "ao cua nga" "costume" false 1 500000)) true)

(define (halloween-sale r num)
  (cond [(empty? r) 0]
        [(cons? r)
         (if (costume? (first r))
             (+ (* (- 1 num) (value(first r))) (halloween-sale (rest r) num))
             (+ (value(first r)) (halloween-sale (rest r) num)))]))
;; halloween-sale: Receipt Number -> Number
;; produces the total cost of the receipt, with the discount applied only to costume merchandise.
(check-expect (halloween-sale rec2 0.5) 812500)
(check-expect (halloween-sale rec5 0.75) 265000)