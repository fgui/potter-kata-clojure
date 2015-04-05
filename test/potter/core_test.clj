(ns potter.core-test
  (:require [clojure.test :refer :all]
            [potter.core :refer :all]))

(def same-four-books [1 1 1 1])
(def four-diff-books [1 2 3 4])
(def six-diff-books [1 2 3 4 5 6])
(def mmm-books [1 2 3 4 5 1 2 3])

(deftest same-book-test
  (testing "all books same price"
    (is (= (price same-four-books) (* 4 book-price)))))

(deftest diff-book-test
  (testing "four different books"
    (is (= (price four-diff-books)
           (apply-discount (* 4 8 ) 0.20)))))

(deftest six-diff-book-test
  (testing "six different books"
    (is (= (price six-diff-books)
           (+ 8 (apply-discount (* 5 8 ) 0.25))))))

(deftest seven-diff-book-test
  (testing "six different books"
    (is (= (price (range 7))
           (+ (apply-discount (* 5 8) 0.25) (apply-discount (* 2 8 ) 0.05))))))

(deftest mmm-books-test
  (testing "mmm 8 books 5-3 or 4-4"
    (is (= (price mmm-books)
           (* 2 (books-price 4))))))
