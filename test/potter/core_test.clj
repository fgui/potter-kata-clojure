(ns potter.core-test
  (:require [clojure.test :refer :all]
            [potter.core :refer :all]))

(def same-four-books [1 1 1 1])
(def four-diff-books [1 2 3 4])
(def six-diff-books [1 2 3 4 5 6])

(deftest same-book-test
  (testing "all books same price"
    (is (= (price same-four-books) (* 4 book-price)))))

(deftest diff-book-test
  (testing "four different books"
    (is (= (price four-diff-books)
           (apply-discount (* 4 8 ) 0.20M)))))

(deftest six-diff-book-test
  (testing "six different books"
    (is (= (price six-diff-books)
           (+ 8 (apply-discount (* 5 8 ) 0.25M))))))
