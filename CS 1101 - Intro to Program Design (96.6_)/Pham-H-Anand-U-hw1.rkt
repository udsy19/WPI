;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname Homework1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #t)))
;Names: Hien Pham, Udaya Tejas Vijay Anand
;Usernames: hpham, uvijayanand

;Question 1,2

(define-struct date (year month day))
;; date is (make-date Natural Natural Natural)
;; interpret: (make-date year month day) represents the date the film opened where
;;            year is the year the film opened
;;            month is the month the film opened
;;            day is the day the film opened

(define DATE1 (make-date 2019 04 26))
(define DATE2 (make-date 2019 03 07))
(define DATE3 (make-date 2022 07 01))
(define DATE4 (make-date 1999 10 22))

;;make-date: Natural Natural Natural -> date

;;date-year: date -> Natural
;;date-month: date -> Natural
;;date-day: date -> Natural

;;date?: date -> Boolean

(define-struct film (title genre rating running-time opening-date nominations))
;;film is (make-film String String String Natural date Natural)
;; interpret: represent a film where
;;      title is the name of the film
;;      genre is the film's genre (drama, comedy, family, etc)
;;      rating is the rate of the film (A rating can be one of G, PG, PG-13, R, NC-17, NR)
;;      running-time is the time of the film in minutes
;;      opening-date is the date of the film opened
;;      nominations is the number of Oscar's nominations the film received

(define FILM1 (make-film "Avengers: Endgame" "Fiction" "PG-13" 182 DATE1 1))
(define FILM2 (make-film "Five Feet Apart" "Romance" "PG-13" 117 DATE2 0))
(define FILM3 (make-film "Minions: The Rise of Gru" "Comedy" "PG" 147 DATE3 0))
(define FILM4 (make-film "Boys Don't Cry" "Romance" "NC-17" 118 DATE4 1))

;;make-film: String String String String Natural date Natural -> film

;;film-title: film -> String
;;film-genre: film -> String
;;film-rating: film -> String
;;film-running-time: film -> Natural
;;film-opening-date: film -> date
;;film-nominations: film -> Natural

;;film?: film -> Boolean

;Question 3
(define (high-brow? film1)
  (cond [[> (film-running-time film1) 150] true]
        [(and (or (equal? (film-rating film1) "NC-17") (equal? (film-rating film1) "NR")) (> (film-nominations film1) 0)) true]
        [else false]))
;; film -> Boolean
;; return true if the film's running-time is > 150 or it was both nominated for an Oscar and has a rating of NC-17 or NR.

(check-expect (high-brow? FILM1) true)
;the film-running-time is greater than 150 minutes
(check-expect (high-brow? FILM2) false)
;the film-running-time is less or equal than 150 minutes and was not nominated for an Oscar and doesn't have a rating of NC-17 and NR
(check-expect (high-brow? FILM4) true)
;the film was both nominated for an Oscar and has a rating of NC-17 or NR.

;Question 4
(define (total-nominations film1 film2)
  (+ (film-nominations film1) (film-nominations film2)))
;; film film -> Number
;; produce the sum of Oscar nominations for the two films

(check-expect (total-nominations FILM1 FILM2) 1)
; checking if the total-nominations is correct

;Question 5
(define (update-nominations film1 num1)
  (make-film (film-title film1) (film-genre film1) (film-rating film1) (film-running-time film1) (film-opening-date film1) num1))
;; film Number -> film
;; produce a film that is the same as the original film except for its nominations is changed to the given nominations

(check-expect (update-nominations FILM1 2) (make-film "Avengers: Endgame" "Fiction" "PG-13" 182 DATE1 2))
; checking if the update is correct

;Question 6
(define (compare date1 date2)
   (cond [[> (date-year date1) (date-year date2)] true]
         [(and (equal? (date-year date1) (date-year date2)) (> (date-month date1) (date-month date2))) true]
         [(and (equal? (date-year date1) (date-year date2)) (equal? (date-month date1) (date-month date2)) (> (date-day date1) (date-day date2))) true]
         [else false]))
(define DATE5 (make-date 2019 04 25))
;; date date -> Boolean
;; helper function that compares date1 and date2, return true if the year of date1 > date2, or month1 > month2 (if the years are the same), or day1 > day2 (if the years and months are the same)

(check-expect (compare DATE1 DATE2) true)
; 2 dates have the same year, but have different months
(check-expect (compare DATE3 DATE1) true)
; 2 dates have different years
(check-expect (compare DATE1 DATE5) true)
; 2 dates have the same year and month, but have different days
(check-expect (compare DATE1 DATE1) false)
; 2 dates are the same

(define (opened-after? film1 date1)
  (cond [(equal? (compare (film-opening-date film1) date1) true) true]
        [else false]))
;; date date -> Boolean
;; comparing date1 with the opening date of film1, return true if film1 is opened after date1

(check-expect (opened-after? FILM1 DATE1) false)
; the film is opened on the same date as the given date
(check-expect (opened-after? FILM1 DATE2) true)
; the film is opened on a different date from the given date
