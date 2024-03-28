import './CategoryDialog.css'
import React, {useState, useEffect} from 'react'

const CategoryDialog = ({isOpen, onClose, category, onSave, onDelete}) => {
	const [name, setName] = useState('')
	const [desc, setDesc] = useState('')
	const [id, setId] = useState(undefined)

	useEffect(() => {
		if(category){
			setId(category.id)
			setName(category.name)
			setDesc(category.description || '')
		}else{
			setId(undefined)
			setName('')
			setDesc('')
		}
	}, [category])

	const handleSave = () => {
		onSave({
			...category,
			name: name,
			description: desc,
			id: id
		})
	}

	const handleDelete = async() => {
		if(category?.id){
			await onDelete(category.id)
			onClose()
		}
	}

	return (
		isOpen && (
			<div id="category-dialog" className="dialog">
				<div className="dialog-content">
					<label>Category Name</label>
					<input
						type="text"
						value={name}
						onChange={(e) => setName(e.target.value)}
					/>
					<label>Description</label>
					<input
						type="text"
						value={desc}
						onChange={(e) => setDesc(e.target.value)}
					/>
					<div className="dialog-actions">
						<button onClick={handleSave}>Save</button>
						<button onClick={handleDelete}>Delete</button>
						<button onClick={onClose}>Cancel</button>
					</div>
				</div>
			</div>
		)
	)
}

export default CategoryDialog
