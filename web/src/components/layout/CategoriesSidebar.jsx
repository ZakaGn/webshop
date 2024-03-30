import React from 'react'
import './CategoriesSidebar.css'

const CategoriesSidebar = ({categories, onCategorySelect}) => {
	return (
		<div className="categories-sidebar">
			<h3>Categories</h3>
			<ul>
				{categories.map((category, index) => (
					<li key={index} onClick={() => onCategorySelect(category)}>{category.name}</li>
				))}
			</ul>
		</div>
	)
}

export default CategoriesSidebar
