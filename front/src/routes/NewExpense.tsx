import React from 'react';
import http from 'RestClient';
import IExpense from 'IExpense';

class NewExpense extends React.Component<any, IExpense>{

    constructor(props: any){
        super(props);
    }

    async handleSubmit(){
        const newState = {...this.state};
        newState.date = new Date();
        try{
            await http('/person', 'POST', this.state);
        }
        catch(error){
            alert(error);
        }
    }


    handleNameChange(event: { target: { value: string; }; }){
        const newState = {...this.state};
        newState.name = event.target.value;
        this.setState(newState);
    }

    handleExpenseChange(event: { target: { value: number; }; }){
        const newState = {...this.state};
        newState.expense = event.target.value;
        this.setState(newState);
    }

    handleDescriptionChange(event: { target: { value: string; }; }){
        const newState = {...this.state};
        newState.description = event.target.value;
        this.setState(newState);
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Name:
                    <input type="text" value={this.state.name} onChange={this.handleNameChange}/>
                </label>
                <label>
                    Expense:
                    <input type="number" value={this.state.expense} onChange={this.handleExpenseChange} />
                </label>
                <label>
                    Description:
                    <input type="number" value={this.state.description} onChange={this.handleDescriptionChange} />
                </label>
                <input type="submit" value="Submit" />
          </form>
        );
    }
}

export default NewExpense;