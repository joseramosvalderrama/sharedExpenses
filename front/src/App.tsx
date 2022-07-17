import React, {Component, ReactNode} from 'react';
import http from 'RestClient';
import IExpense from 'IExpense';
import Expense from 'Expense';
import { Link } from 'react-router-dom';
import Balance from 'Balance';
import Duty from 'Duty';
import IBalance from 'IBalance';
import IDuty from 'IDuty';

interface IState{
  expenses: IExpense[],
  balances: IBalance[],
  duties: IDuty[]
}

class App extends Component<any, IState>{

  constructor(props:any){
    super(props);
    this.state = {
      expenses: [],
      balances: [],
      duties: []
    }
  }

  componentDidMount(){
    this.retrieveExpenses();
    this.retrieveBalances();
    this.retrieveDuties();
  }

  async retrieveBalances(){
    try{
      const balances = await http<IBalance[]>('/person', 'GET');
      this.setState({balances: balances});
    }
    catch(error){
      alert(error);
    }
  }

  async retrieveExpenses(){
    try{
      const expenses = await http<IExpense[]>('/expense', 'GET');
      expenses.sort((a,b) => b.date - a.date);
      this.setState({expenses: expenses});
    }
    catch(error){
      alert(error);
    }
  }

  async retrieveDuties(){
    try{
      const duties = await http<IDuty[]>('/duty', 'GET');
      this.setState({duties: duties});
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
          <h3>Gastos</h3>
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
        <div className='duties'>
          <h3>Deudas</h3>
          {
            this.state.duties.map(el => {
              return <Duty debtor={el.debtor} payer={el.payer} amount={el.amount}/>
            })
          }
        </div>
      </div>
    );
  }
}

export default App;
