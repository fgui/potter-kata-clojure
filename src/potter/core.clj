(ns potter.core)

(def book-price 8)

(defn apply-discount [price discount]
  (- price (* price discount))
  )

(def books-price
  {1 8
   2 (apply-discount (* 2 8) 0.05)
   3 (apply-discount (* 3 8) 0.10)
   4 (apply-discount (* 4 8) 0.20)
   5 (apply-discount (* 5 8) 0.25)})

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
          books-process (min 5 (count diff-books-count))
          ]
      (if (> books-process 1)
        ;; test remove max and max-1
        (+ aprice (min (price-diff-books-count
                 (books-price books-process)
                 (remove-books diff-books-count books-process))
                (price-diff-books-count
                 (books-price (-  books-process 1))
                 (remove-books diff-books-count (- books-process 1)))
                ))
        (+ aprice (price-diff-books-count
                   (books-price books-process)
                   (remove-books diff-books-count books-process)))
        )
      )
    )
)

(defn price [books]
  (price-diff-books-count 0 (diff-books-counts books))
  )
