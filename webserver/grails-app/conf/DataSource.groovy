environments {
    development {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_categories"
            }
        }
    }
    test {
        grails {
            mongo {
                host = "localhost"
                databaseName = "mb_categories"
            }
        }
    }
    production {
        grails {
            mongo {

                // replicaSet = []
                host = "localhost"
                username = ""
                password = ""
                databaseName = "mb_categories"
            }
        }
    }
}