package categories

class Category {

    static constraints = {
       // categoryId          nullable: false, maxSize: 20, unique: true
        parentCategoryId    nullable:true, maxSize: 20
        name                blank:false, maxSize: 150
        status              nullable:false, blank:false, inList: ["active", "checking", "canceled","deleted"]
        dateRegistration    nullable:false
        dateUpdate          nullable:false
        siteId              nullable:false

    }

    String parentCategoryId
    String name
    String status = "active"
    Date dateRegistration = new Date()
    Date dateUpdate = new Date()
    String siteId = 'MB'

    //static hasMany = [childrenCategories:Category]




}
