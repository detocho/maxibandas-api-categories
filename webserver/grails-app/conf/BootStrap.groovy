import categories.Category

class BootStrap {

    def init = { servletContext ->
        test{

        }
        development{

            if (Category.count() == 0){
                def category01 = new Category(
                        name:"Generos musicales"
                )

                category01.save()

                def category02 = new Category(
                        name:"Mariachi",
                        parentCategoryId: category01.id
                )

                category02.save()

                def category03 = new Category(
                        name: "Norteño",
                        parentCategoryId: category01.id
                )
                category03.save()
            }

        }
        production{

        }
    }
    def destroy = {
    }
}
