class User{
	constructor(data){
		if(!data) return
		this.id = data.id
		this.firstName = data.firstName
		this.lastName = data.lastName
		this.email = data.email
		this.role = data.role
	}
}

export default User
