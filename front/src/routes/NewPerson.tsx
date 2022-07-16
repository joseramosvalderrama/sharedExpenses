import React from 'react';
import http from 'RestClient';

interface IPerson {
    name: string
}

class NewPerson extends React.Component<any, IPerson>{

    constructor(props: any){
        super(props);
        this.state = {
            name: ''
        }
    }

    handleSubmit = async() => {
        try{
            await http('/person', 'POST', this.state);
        }
        catch(error: any){
            if(!error.contains("Failed to fetch")){
                alert(error);
            }
        }
    }

    handleChange = (event: { target: { value: string; }; }) => {
        this.setState({name: event.target.value});
    }

    render(){
        return (
            <div className='form'>
                <h2>Persona nueva</h2>
                <form onSubmit={this.handleSubmit} action="/">
                    <label>
                    Name:
                    <input required type="text" value={this.state.name} onChange={this.handleChange}/>
                    </label>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        )
    }
}

export default NewPerson;