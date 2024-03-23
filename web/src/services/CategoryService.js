import {api} from 'utils/api'

export const categoryService = {
	fetchCategories: async() => api.fetchCategories(),
	addCategory: async(category) => api.addCategory(category),
	updateCategory: async(id, category) => api.updateCategory(id, category),
	deleteCategory: async(id) => api.deleteCategory(id)
}
