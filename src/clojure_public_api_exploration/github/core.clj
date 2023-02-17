(ns clojure-public-api-exploration.github.core
  (:require
   [cheshire.core :as json]
   [clj-http.client :as client]
   [clj-yaml.core :as yaml]))

(def secrets (yaml/parse-string (slurp "secrets.yaml")))
(def github-token (:github-token secrets))
(def basic-auth-str (str "wahidyankf:" github-token))
(def client-config {:basic-auth basic-auth-str})

(-> (client/get "https://api.github.com/users/wahidyankf/repos" client-config)
    :body
    json/decode)


(def raw-res (client/get "https://api.github.com/repos/wahidyankf/dotfiles/commits" client-config))

(def res (-> raw-res
             :body
             json/decode))

(->> res
     (map (fn [x]
            (-> x
                (get "commit")
                (get "committer")))))

(def raw-activity (-> (client/get "https://api.github.com/repos/wahidyankf/dotfiles/commits" client-config)
                      :body
                      json/decode))
(-> raw-activity
    first
    (get "commit")
    (get "committer"))