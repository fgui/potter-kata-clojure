(ns potter.core)

(def book-price 8)

(defn apply-discount [price discount]
  (- price (* price discount))
  )

(def ini-books-price
  {1 8
   2 (apply-discount (* 2 8) 0.05)
   3 (apply-discount (* 3 8) 0.10)
   4 (apply-discount (* 4 8) 0.20)
   5 (apply-discount (* 5 8) 0.25)})

;; TODO genereta this dinamically base in total number of different books
(def books-price
  (merge ini-books-price
         {6 (+ (ini-books-price 5) (ini-books-price 1))
          7 (+ (ini-books-price 4) (ini-books-price 3))})
  )

(defn diff-books-counts [books]
  (sort > (map count (vals (group-by identity books))))
)

(defn remove-books [book-counts n]
  (sort >
        (filter pos?
                (map #(- %1 %2) book-counts
                     (concat  (take n (repeat 1)) (repeat 0))))))

(defn price-diff-books-count [aprice diff-books-count]
  (if (empty? diff-books-count)
    aprice
    (let [
          books-process (min 7 (count diff-books-count))
          price (books-price  books-process)
          books-left (remove-books diff-books-count books-process)
          ]
      (recur (+ aprice price) books-left)
      )
    )
)

(defn price [books]
  (price-diff-books-count 0 (diff-books-counts books))
  )
