(ns potter.core)

(def book-price 8)

(defn apply-discount [price discount]
  (- price (* price discount))
  )

(def books-price
  {1 book-price
   2 (apply-discount (* 2 book-price) 0.05)
   3 (apply-discount (* 3 book-price) 0.10)
   4 (apply-discount (* 4 book-price) 0.20)
   5 (apply-discount (* 5 book-price) 0.25)})

(defn diff-books-counts [books]
  (sort > (vals (frequencies books)))
)

(defn remove-books [book-counts n]
  (sort >
        (filter pos?
                (map #(- %1 %2) book-counts
                     (concat (repeat n 1) (repeat 0))))))

(defn price-diff-books-count [acc-price diff-books-count]
  (if (empty? diff-books-count)
    acc-price
    (let [books-process (min 5 (count diff-books-count))]
      (if (> books-process 1)
        ;; test remove max and max-1 and take min price.
        (min (price-diff-books-count
              (+ acc-price (books-price books-process))
              (remove-books diff-books-count books-process))
             (price-diff-books-count
              (+ acc-price (books-price (-  books-process 1)))
              (remove-books diff-books-count (- books-process 1)))
             )
        (recur
           (+ acc-price (books-price books-process))
           (remove-books diff-books-count books-process))
        )
      )
    )
)

(defn price [books]
  (price-diff-books-count 0 (diff-books-counts books))
  )
