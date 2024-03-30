import React, {useState} from 'react'
import CategoryService from 'services/CategoryService'
import {toast} from 'react-toastify'

const AddCategoryForm = ({onClose, onCategoryAdded}) => {
	const [name, setName] = useState('')
	const [description, setDescription] = useState('')
	const [isLoading, setIsLoading] = useState(false)

	const handleSubmit = async(event) => {
		event.preventDefault()
		setIsLoading(true)

		try{
			const addedCategory = await CategoryService.addCategory({name, description})
			onCategoryAdded(addedCategory.data)
			onClose()
			toast.success('Category added successfully!')
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to add category. Please try again.'
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
			<button type="submit" disabled={isLoading}>{isLoading ? 'Adding...' : 'Add Category'}</button>
			<button type="button" onClick={onClose} disabled={isLoading}>Cancel</button>
		</form>
	)
}

export default AddCategoryForm
