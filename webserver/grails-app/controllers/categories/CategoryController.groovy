package categories

import javax.servlet.http.HttpServletResponse
import grails.converters.*
//import grails.transaction.*
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*
import grails.plugin.gson.converters.GSON
import categories.exceptions.NotFoundException
import categories.exceptions.ConflictException
import categories.exceptions.BadRequestException

class CategoryController {

    def categoryService


    def notAllowed(){
        def method = request.method

        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
        response.setContentType("application/json; charset=utf-8")

        def mapResult = [
                message: "Method $method not allowed",
                status: HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                error:"not_allowed"
        ]
        render mapResult as GSON
    }

    def getCategory(){

        def categoryId = params.categoryId
        def result

        try{
            result = categoryService.getCategory(categoryId)
            response.setStatus(HttpServletResponse.SC_OK)
            response.setContentType "application/json; charset=utf-8"
            render result as GSON

        }catch (NotFoundException e){
            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.setContentType "application/json; charset=utf-8"
            def mapExcepction = [
                    message: e.getMessage(),
                    status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    error: "internal_server_error"
            ]
            render mapExcepction as GSON
        }
    }

    def addCategory(){

        def result
        def parentCategoryId = params.categoryId

        try{
            if(!parentCategoryId){
                result = categoryService.createCategory(request.JSON)
            }else {
                result = categoryService.createCategory(parentCategoryId, request.JSON)
            }
            response.setStatus( HttpServletResponse.SC_CREATED)
            response.setContentType "application/json; charset=utf-8"
            render result as GSON

        }catch (BadRequestException e) {

            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON

        }catch(NotFoundException e){

            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON


        }catch (Exception e){

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.setContentType "application/json; charset=utf-8"
            def mapExcepction = [
                    message: e.message,
                    status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    error: "internal_server_error"
            ]
            render mapExcepction as GSON

        }

    }

    def putCategory(){

        def result
        def categoryId = params.categoryId

        try{

            result = categoryService.modifyCategory(categoryId, request.JSON)
            response.setStatus( HttpServletResponse.SC_OK)
            response.setContentType "application/json; charset=utf-8"
            render result as GSON

        }catch(NotFoundException e){

            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON

        }catch (BadRequestException e) {

            response.setStatus(e.status)
            response.setContentType "application/json; charset=utf-8"

            def mapExcepction = [
                    message: e.getMessage(),
                    status: e.status,
                    error: e.error
            ]
            render mapExcepction as GSON

        }catch (Exception e){

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.setContentType "application/json; charset=utf-8"
            def mapExcepction = [
                    message: e.message,
                    status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    error: "internal_server_error"
            ]
            render mapExcepction as GSON

        }
    }
}
