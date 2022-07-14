import React from 'react';
import http from 'RestClient';

interface IPerson {
    name: string
}

class NewPerson extends React.Component<any, IPerson>{

    constructor(props: any){
        super(props);
    }

    async handleSubmit(){
        try{
            await http('/person', 'POST', this.state);
        }
        catch(error){
            alert(error);
        }
    }

    handleChange(event: { target: { value: string; }; }){
        this.setState({name: event.target.value});
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                Name:
                <input type="text" value={this.state.name} onChange={this.handleChange}/>
                </label>
                <input type="submit" value="Submit" />
          </form>
        )
    }
}

export default NewPerson;