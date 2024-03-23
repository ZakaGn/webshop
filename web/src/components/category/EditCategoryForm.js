import React, {useState, useEffect} from 'react'
import {categoryService} from 'services/CategoryService'
import {toast} from "react-toastify";

const EditCategoryForm = ({category, onClose, onCategoryUpdated}) => {
	const [name, setName] = useState('')
	const [description, setDescription] = useState('')
	const [isLoading, setIsLoading] = useState(false)

	useEffect(() => {
		if(category){
			setName(category.name)
			setDescription(category.description)
		}
	}, [category])

	const handleSubmit = async(event) => {
		event.preventDefault()
		setIsLoading(true)

		try{
			const updatedCategory = await categoryService.updateCategory(category.id, {name, description})
			onCategoryUpdated(updatedCategory.data)
			onClose()
			toast.success('Category updated successfully!')
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to update category. Please try again.'
			toast.error(errorMessage)
		}finally{
			setIsLoading(false)
		}
	}

	return (
		<form onSubmit={handleSubmit}>
			<div>
				<label htmlFor="name">Category Name:</label>
				<input
					type="text"
					id="name"
					value={name}
					onChange={(e) => setName(e.target.value)}
					required
				/>
			</div>
			<div>
				<label htmlFor="description">Description:</label>
				<textarea
					id="description"
					value={description}
					onChange={(e) => setDescription(e.target.value)}
				/>
			</div>
			<button type="submit" disabled={isLoading}>
				{isLoading ? 'Updating...' : 'Update Category'}
			</button>
			<button type="button" onClick={onClose} disabled={isLoading}>
				Cancel
			</button>
		</form>
	)
}

export default EditCategoryForm
