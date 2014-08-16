class UrlMappings {

	static mappings = {

		"/$categoryId?" {
            controller = "Category"
            action = [GET: 'getCategory', POST:'notAllowed',PUT:'notAllowed' ,DELETE: 'notAllowed']
        }
	}
}
