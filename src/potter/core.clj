(ns potter.core)

(def book-price 8M)
(def discount-diff-books
  {1 0M
   2 0.05M
   3 0.10M
   4 0.20M
   5 0.25M})

(defn apply-discount [price discount]
  (- price (* price discount))
  )

(defn diff-books-counts [books]
  (sort > (map count (vals (group-by identity books))))
)

(defn price-diff-books-count [aprice diff-books-count]
  (if (empty? diff-books-count)
    aprice
    (let [diff-books (count diff-books-count)
          books-process (min 5 diff-books)
          books-minus-lst (concat (take books-process (repeat 1))
                                  (take (- diff-books books-process) (repeat 0)))
          price (apply-discount
                 (* book-price books-process)
                 (discount-diff-books books-process))
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
