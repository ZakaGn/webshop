import React, {useState, useEffect} from 'react'
import {categoryService} from 'services/CategoryService'
import CategoryCard from 'components/category/CategoryCard'
import AddCategoryForm from 'components/category/AddCategoryForm'
import EditCategoryForm from 'components/category/EditCategoryForm'
import {toast} from "react-toastify";

const CategoriesList = () => {
	const [categories, setCategories] = useState([])
	const [isAdding, setIsAdding] = useState(false)
	const [isEditing, setIsEditing] = useState(false)
	const [currentCategory, setCurrentCategory] = useState(null)

	useEffect(() => {
		fetchCategories()
	}, [])

	const fetchCategories = async() => {
		try{
			const response = await categoryService.fetchCategories()
			setCategories(response.data)
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to fetch categories'
			toast.error(errorMessage)
		}
	}

	const handleAddClick = () => {
		setIsAdding(true)
	}

	const handleEditClick = (category) => {
		setCurrentCategory(category)
		setIsEditing(true)
	}

	const handleDelete = async(id) => {
		try{
			await categoryService.deleteCategory(id)
			await fetchCategories()
			toast.success('Category deleted successfully')
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to delete category'
			toast.error(errorMessage)
		}
	}

	const handleCloseForm = () => {
		setIsAdding(false)
		setIsEditing(false)
		setCurrentCategory(null)
		fetchCategories()
	}

	return (
		<div>
			<h2>Categories</h2>
			<button onClick={handleAddClick}>Add New Category</button>
			{categories.map((category) => (
				<CategoryCard
					key={category.id}
					category={category}
					onEdit={() => handleEditClick(category)}
					onDelete={() => handleDelete(category.id)}
				/>
			))}
			{isAdding && (
				<AddCategoryForm
					onClose={handleCloseForm}
				/>
			)}
			{isEditing && currentCategory && (
				<EditCategoryForm
					category={currentCategory}
					onClose={handleCloseForm}
				/>
			)}
		</div>
	)
}

export default CategoriesList
