(ns com.moclojer.config
  (:require [babashka.cli :as cli]))

(def version
  "get version from pom properties"
  "0.3.2")

;; https://specifications.freedesktop.org/basedir-spec/latest/ar01s03.html
(def get-home (System/getProperty "user.home"))

(def get-xdg-config-home
  "Get the XDG_CONFIG_HOME or HOME/.config if its not defined."
  (or (System/getenv "XDG_CONFIG_HOME")
      (str get-home "/.config")))

(defn with-xdg
  "Will prefix with the XDG home."
  [s] (str get-xdg-config-home "/" s))

(def spec
  "Configuration Parameters"
  {:config {:ref     "<file>"
            :desc    "Config path <file> or the CONFIG enviroment variable."
            :alias   :c
            :default "moclojer.yml"}
   :mocks  {:ref     "<file>"
            :desc    "OpenAPI v3 mocks path <file> or the MOCKS enviroment variable."
            :alias   :m}
   :version {:desc   "Show version."
             :alias  :v}
   :format {:ref     "<format>"
            :desc    "Output format."
            :alias   :f
            :default "println"}
   :help    {:desc   "Show this Help."
             :alias  :h}})

(def empty-args
  "Args are empty"
  (str "moclojer (" version "), simple and efficient HTTP mock server.\r\n"
       (cli/format-opts {:spec spec :order [:config :mocks :version :help]})))
