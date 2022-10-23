(ns mybank-web-api.interceptors 
  (:require [io.pedestal.interceptor :as i]))

(defn response
  "Create a map representing the response of a HTTP Request, using 'status', 'body' and the rest as headers."
  [status body & {:as headers}]
  {:status  status
   :body    body
   :headers headers})

(def ok (partial response 200))
(def created (partial response 201))
(def accepted (partial response 202))
(def error (partial response 500))

(def echo
  {:name ::echo
   :enter #(assoc % :response (ok (:request %)))})

(defn print-n-continue
  "Logs the context and return the same."
  [context]
  (println "LOG:" context)
  context)

(def print-n-continue-int
  (i/interceptor {:name  :print-n-continue
                  :enter print-n-continue
                  :leave print-n-continue}))

(defn hello
  [request]
  (ok "Hello!"))

(def hello-interceptor
  (i/interceptor {:name ::hello
                  :enter #(->> % ;;ctx
                               hello
                               (assoc % :response))}))


;; (defonce contas (atom {:1 {:saldo 100}
;;                        :2 {:saldo 200}
;;                        :3 {:saldo 300}}))

;; (defn add-contas-atom [context]
;;   (update context :request assoc :contas contas))

;; (def contas-interceptor
;;   {:name  :contas-interceptor
;;    :enter add-contas-atom})

;; (defn get-saldo [{{:keys [id]} :path-params}]
;;   (let [id-conta (keyword id)]
;;     {:status  200
;;      :headers {"Content-Type" "text/plain"}
;;      :body    (id-conta @contas "conta inválida!")})) ;; Aqui ele vai pegar a conta existente ou falar q a conta é invalida??

;; (defn make-deposit! [{{:keys [id]} :path-params
;;                       body         :body}]

;;   (let [id-conta (keyword id)
;;         valor-deposito (-> body slurp parse-double)
;;         SIDE-EFFECT! (swap! contas (fn [m] (update-in m [id-conta :saldo] #(+ % valor-deposito))))]
;;     {:status  200
;;      :headers {"Content-Type" "text/plain"}
;;      :body    {:id-conta   id-conta
;;                :novo-saldo (id-conta @contas)}}))
