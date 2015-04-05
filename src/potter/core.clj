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

(defn price-diff-books-count [aprice diff-books-count]
  (if (empty? diff-books-count)
    aprice
    (let [diff-books (count diff-books-count)
          books-process (min 7 diff-books)
          books-minus-lst (concat (take books-process (repeat 1))
                                  (take (- diff-books books-process) (repeat 0)))
          price (books-price
                 books-process)
          books-left (sort >
                           (filter pos?
                                   (map #(- %1 %2) diff-books-count books-minus-lst)))
          ]
      (recur (+ aprice price) books-left)
      )
    )
)

(defn price [books]
  (price-diff-books-count 0 (diff-books-counts books))
  )
