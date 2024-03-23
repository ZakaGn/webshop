import React from 'react'

const CategoryCard = ({category, onEdit, onDelete}) => {
	return (
		<div className="category-card">
			<div className="category-info">
				<h3>{category.name}</h3>
				<p>{category.description}</p>
			</div>
			<div className="category-actions">
				<button className="edit-button" onClick={() => onEdit(category)}>Edit</button>
				<button className="delete-button" onClick={() => onDelete(category.id)}>Delete</button>
			</div>
		</div>
	)
}

export default CategoryCard
