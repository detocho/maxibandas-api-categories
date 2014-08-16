package categories

import java.text.MessageFormat
import org.apache.ivy.plugins.conflict.ConflictManager
import grails.converters.*
import categories.exceptions.NotFoundException
import categories.exceptions.ConflictException
import categories.exceptions.BadRequestException

class CategoryService {

   def getCategory( def categoryId){

       Map jsonResult   = [:]
       def jsonChildren = []
       Map resultParentCategory = [:]

       if (!categoryId){
           throw  new NotFoundException("You must provider categoryId")
       }

       def category = Category.findById(categoryId)

       if (!category){
           throw  new NotFoundException("The categoryId not found")
       }

       def childrenCategories = Category.findAllByParentCategoryId(categoryId)
       childrenCategories.each{
           jsonChildren.add(
                   categoryId : it.id,
                   name : it.name
           )
       }



       if(category.parentCategoryId){

           def parentCategory = Category.findById(category.parentCategoryId)
           resultParentCategory.id  = parentCategory.id
           resultParentCategory.name = parentCategory.name

       }

       jsonResult.id  = category.id
       jsonResult.name = category.name
       jsonResult.parent_category=resultParentCategory
       jsonResult.children_categories = jsonChildren


       jsonResult

   }

   /*

   def createCategory(def parentCategoryId,def jsonCategory){

   }

   def modifyCategory(def categoryId, def jsonCategory){

   }*/
}
