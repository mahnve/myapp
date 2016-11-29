(def project 'myapp)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src/clj" "src/cljs"}
          :source-paths   #{"test/clj" "test/cljs"}
          :dependencies   '[[org.clojure/clojure "1.8.0"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [yada "1.1.44"]
                            [rum "0.10.7"]
                            [aleph "0.4.1"]
                            [bidi "2.0.14"]
                            [mount "0.1.10"]
                            [adzerk/boot-cljs "1.7.228-2"]
                            [adzerk/boot-reload "0.4.13"]
                            [org.clojure/clojurescript "1.9.216"]
                            [samestep/boot-refresh "0.1.0"]
                            [adzerk/boot-cljs-repl "0.3.3"]])

(task-options!
 aot {:namespace   #{'myapp.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/myapp"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'myapp.core
      :file        (str "myapp-" version "-standalone.jar")})

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.boot-reload :refer [reload]]
         '[samestep.boot-refresh :refer [refresh]]
         '[adzerk.boot-cljs :refer [cljs]])

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask dev-system []
  (comp (watch)
        (refresh)
        (repl)))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[myapp.core :as app])
  (apply (resolve 'app/-main) args))

