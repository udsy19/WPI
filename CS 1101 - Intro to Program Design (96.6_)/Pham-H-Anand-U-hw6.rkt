;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Pham-H-Anand-U-hw6) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))
;Names: Hien Pham, Udaya Tejas Vijay Anand
;Usernames: hpham, uvijayanand


;Question 1
(define-struct message (username text read?))
;; a Message is (make-message String String Boolean)
;; interpret: represents a message where
;;     username is the sender's username
;;     text is the text of the message
;;     read? indicates whether or not the (recipient) user has read the message

(define-struct user (username mailbox))
;; an User is (make-user String ListOfMessage)
;; interpret: represents an user where
;;     username is the username
;;     mailbox is the mailbox of the user

;; a mailbox (ListOfMessage) is one of
;;   empty
;;   (cons Message ListOfMessage)

;; an Email-system is one of
;;    empty
;;    (cons user ListOfUser)

;Question 2
(define mailsys empty)
;; mailsys is a ListOfUser with no user (empty list)
(define newuser (make-user "Newuser" empty))
;; newuser is an user data structure with username "Newuser" and mailbox (ListOfMessage) as an empty list

;Question 3

(define (adding-user a-username a-lou)
  (cons (make-user a-username empty) a-lou))
;; String ListOfUser -> ListOfUser
;; helper function to add an user to a list of user
(check-expect (adding-user "hien" (list (make-user "ngu" empty)
                                        (make-user "nhu" empty)
                                        (make-user "bo" empty)))
              (list (make-user "hien" empty)
                    (make-user "ngu" empty)
                    (make-user "nhu" empty)
                    (make-user "bo" empty)))
(check-expect (adding-user "dung" (list (make-user "oc" empty)
                                        (make-user "cho" empty)))
              (list (make-user "dung" empty)
                    (make-user "oc" empty)
                    (make-user "cho" empty)))

(define (add-new-user a-username)
  (set! mailsys (adding-user a-username mailsys)))
;; String -> Void
;; add a new user with the given username to the mail system
;; EFFECT: changes the mailsys by adding a newuser to it

;Question 4

(define (find-user name mailsystem)
  (if (equal? name (user-username (first mailsystem)))
      (first mailsystem)
      (find-user name (rest mailsystem))))
;; String Email-system -> user
;; helper function to find an user in the email system

(check-expect (find-user "Hien" (list (make-user "Duy" (list (make-message "hello" "bot ngu gium" false)))
                                   (make-user "Hien" (list (make-message "nga" "ngu vl" true)))
                                   (make-user "Ha" empty)))
              (make-user "Hien" (list (make-message "nga" "ngu vl" true))))
(check-expect (find-user "mylinh" (list (make-user "pnl" (list (make-message "con cho" "ham lon" false)))
                                        (make-user "hnga" (list (make-message "nghe chi" "cai loz" true)))
                                        (make-user "mylinh" (list (make-message "anh" "xin loi em" false)))))
              (make-user "mylinh" (list (make-message "anh" "xin loi em" false))))

(define (sending sender name text mailsystem)
  (if (equal? name (user-username (first mailsystem)))
      (append (list (make-message sender text false)) (user-mailbox (first mailsystem)))
      (sending sender name text (rest mailsystem))))
;; String String String Email-system -> mailbox
;; helper function to store a new unread message
(check-expect (sending "Hien" "Nga" "em nhu loz" (list (make-user "Nga" empty)
                                                       (make-user "huhu" empty)))
              (list (make-message "Hien" "em nhu loz" false)))

(check-expect (sending "Hien" "Mylinh" "dm anh xin loi" (list (make-user "Duy" empty)
                                                              (make-user "Mylinh" empty)))
              (list (make-message "Hien" "dm anh xin loi" false)))

(define (send-email-message sender name text)
  (set-user-mailbox! (find-user name mailsys) (sending sender name text mailsys)))
;; String String String -> Void
;; consumes the name of the sender of an email, the name of the recipient of the email, and the text of an email message, and produces void
;; EFFECT: store a new unread message in the recipient's mailbox.


;; Question 5

(define (getall name mailsystem)
  (if (equal? name (user-username (first mailsystem)))
      (user-mailbox (first mailsystem))
      (getall name (rest mailsystem))))
;; String Email-system -> ListOfMessage
;; helper function to get all the message

(check-expect (getall "Hien" (list (make-user "Duy" (list (make-message "hello" "bot ngu gium" false)))
                                   (make-user "Hien" (list (make-message "nga" "ngu vl" true)))
                                   (make-user "Ha" empty)))
              (list (make-message "nga" "ngu vl" true)))
(check-expect (getall "mylinh" (list (make-user "pnl" (list (make-message "con cho" "ham lon" false)))
                                        (make-user "hnga" (list (make-message "nghe chi" "cai loz" true)))
                                        (make-user "mylinh" (list (make-message "anh" "xin loi em" false)))))
              (list (make-message "anh" "xin loi em" false)))

(define (get-all-unread-messages name)
  (local [(define name-mailbox (getall name mailsys))]
    (begin
      (local [(define (read-message user)
                (make-message (message-username user) (message-text user) true))
              (define (set-mail user)
                (set-user-mailbox! (find-user name mailsys) (map read-message user)))]
        (set-mail name-mailbox))
      (local
        [(define (unread? mess)
           (equal? (message-read? mess) false))]
        (filter unread? name-mailbox)))))
;; String -> ListOfMessage
;; consumes a username and produces a list of messages. The produced list contains the unread messages in the mailbox of the user with the given name.
;; EFFECT: All unread messages in the named user's mailbox have been set to read.

;; Question 6
(define (most-total-messages)
  (local [(define (most-total-messages mailsystem start user-start)
            (cond [(empty? mailsystem)
                   (if (and (equal? start 0) (equal? user-start 0))
                                        "Error, no user in system"
                                        user-start)]
                  [else
                   (if (>= (length (user-mailbox (first mailsystem))) start)
                       (most-total-messages (rest mailsystem) (length (user-mailbox (first mailsystem))) (first mailsystem))
                       (most-total-messages (rest mailsystem) start user-start))]))]
    (most-total-messages mailsys 0 0 )))
;; most-total-messages: void -> user
;; produces the user in the mailsystem with the largest number of messages in his/her mailbox

;; Question 7
(add-new-user "Hien")
(add-new-user "Nga")
(add-new-user "mylinh")
(add-new-user "Duy")
(send-email-message "Hai" "Hien" "hello")
(send-email-message "Hien" "mylinh" "hoi nho em")
(send-email-message "Nguyen" "Duy" "MU tuoi lon")
(get-all-unread-messages "Hien")
(get-all-unread-messages "Duy")
(send-email-message "Hien" "mylinh" "minh quay lai duoc khong")
(get-all-unread-messages "mylinh")
(most-total-messages)

;; Question 8
(define (sum-of-string-lengths los)
  (local [(define (sum los acc)
            (if (empty? los)
                acc
                (sum (rest los) (+ acc (string-length (first los))))))]
    (sum los 0)))
;;sum-of-string-lengths: ListOfString -> Natural
;;consumes a ListOfString and produces the sum of the lengths of the strings in the list.


(check-expect (sum-of-string-lengths (list "hihi" "haha" "mlinh")) 13)
(check-expect (sum-of-string-lengths (list "hien" "oi" "dung" "ngu" "nua")) 16)
(check-expect (sum-of-string-lengths (list)) 0)

;; Question 9
(define (one-long-string los)
  (local [(define (one-long-string los acc)
            (if (empty? los)
                acc
                (one-long-string (rest los) (string-append acc (first los)))))]
    (one-long-string los "")))
;; one-long-string: ListOfString -> String
;; consumes a ListOfString and produces the concatenation of strings in the list in the order they appear in the list.
(check-expect (one-long-string (list)) "")
(check-expect (one-long-string (list "a" "b" "c")) "abc")