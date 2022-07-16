import React, {Component, ReactNode} from 'react';
import http from 'RestClient';
import IExpense from 'IExpense';
import Expense from 'Expense';
import { Link } from 'react-router-dom';
import Balance from 'Balance';

interface IState{
  expenses: IExpense[],
  balances: IBalance[]
}

interface IBalance{
  name: string,
  balance: number
}

class App extends Component<any, IState>{

  constructor(props:any){
    super(props);
    this.state = {
      expenses: [],
      balances: []
    }
  }

  async componentDidMount(){
    this.retrieveExpenses();
    this.retrieveBalances();
  }

  async retrieveBalances(){
    try{
      const balances = await http<IBalance[]>('/person', 'GET');
      const newState = {...this.state};
      newState.balances = balances;
      this.setState(newState);
    }
    catch(error){
      alert(error);
    }
  }

  async retrieveExpenses(){
    try{
      const expenses = await http<IExpense[]>('/expense', 'GET');
      expenses.sort((a,b) => b.date - a.date);
      const newState = {...this.state};
      newState.expenses = expenses;
      this.setState(newState);
    }
    catch(error){
      alert(error);
    }
  }

  render(): ReactNode {
    return(
      <div className='App'>
        <h1>Gastos compartidos</h1>
        <div className='links'>
          <div className='link'>
            <Link to="/person/new">Añadir amigo</Link>
          </div>
          <div className='link'>
            <Link to="/expense/new">Añadir gasto</Link>
          </div>
        </div>
        <div className='expenses'>
          {
            this.state.expenses.map(el => {
              return <Expense person = {el.person} cost = {el.cost} description = {el.description} date = {el.date} />;
            })
          }
        </div>
        <div className='balances'>
          <h3>Balance</h3>
          {
            this.state.balances.map(el => {
              return <Balance name={el.name} balance={el.balance}/>;
            })
          }
        </div>
      </div>
    );
  }
}

export default App;
