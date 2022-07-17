import React from 'react';
import http from 'RestClient';
import IExpense from 'IExpense';
import Select, { OptionsOrGroups } from 'react-select';

interface IState{
    expense: {
        cost: number,
        description: string,
        date: Date
    },
    people: IPerson[],
    selectedPerson: Option | null
}

interface IPerson{
    id: number,
    name: string
}

interface Option{
    label: string,
    value: number
}

class NewExpense extends React.Component<any, IState>{

    constructor(props: any){
        super(props);
        this.state = {
            expense: {
                cost: 0.01,
                description: '',
                date: new Date()
            },
            people: [],
            selectedPerson: null
        }
    }

    async componentDidMount(){
        try{
            const people = await http<IPerson[]>('/person', 'GET');
            this.setState({
                people: people,
                selectedPerson: {
                    value: people[0].id,
                    label: people[0].name
                }
            });
          }
          catch(error){
            alert(error);
        }
    }

    handleSubmit = async () => {
        const expense = {...this.state.expense};
        expense.date = new Date();
        const personId = this.state.selectedPerson?.value;
        try{
            await http('/person/' + personId + '/expense', 'POST', expense);
        }
        catch(error: any){
            if(!error.contains("Failed to fetch")){
                alert(error);
            }
        }
    }

    handleExpenseChange = (event: { target: { value: string; }; }) => {
        const newState = {...this.state};
        newState.expense.cost = +event.target.value;
        this.setState(newState);
    }

    handleDescriptionChange = (event: { target: { value: string; }; }) => {
        const newState = {...this.state};
        newState.expense.description = event.target.value;
        this.setState(newState);
    }

    handlePersonChange = (option: Option | null) => {
        const newState = {...this.state};
        newState.selectedPerson = option;
        this.setState(newState);
    }

    render(){
        const peopleOptions: Option[] = 
            this.state.people.map(el => {
                return {value: el.id, label: el.name}
            });

        return (
            <div className='form'>
                <form onSubmit={this.handleSubmit} action="/">
                    <h2>Nuevo gasto</h2>
                    <label>
                        Cost (€):
                        <input type="number" value={this.state.expense.cost} onChange={this.handleExpenseChange}/>
                    </label>
                    <label>
                        Description:
                        <input required type="text" placeholder='Descripción' value={this.state.expense.description} onChange={this.handleDescriptionChange} />
                    </label>
                    <label>   
                        Person:                 
                        <Select
                            options={peopleOptions}
                            value={this.state.selectedPerson}
                            onChange={this.handlePersonChange}
                        />
                    </label>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default NewExpense;