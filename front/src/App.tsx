import React, {Component, ReactNode} from 'react';
import './App.css';
import http from 'RestClient';
import IExpense from 'IExpense';
import Expense from 'Expense';
import { Link } from 'react-router-dom';

class App extends Component<any,[IExpense]>{

  constructor(props:any){
    super(props);
    this.retrieveData();
  }

  async retrieveData(){
    const data = await http<[IExpense]>('/expenses', 'GET');
    this.state = data;
  }

  render(): ReactNode {
    return(
      <div className='App'>
        <div className='link'>
          <Link to="/person/new">Añadir amigo</Link>
          <Link to="/expense/new">Añadir gasto</Link>
        </div>
        <div className='expenses'>
          {
            this.state.map(el => {
              return <Expense name = {el.name} expense = {el.expense} description = {el.description} date = {el.date} />;
            })
          }
        </div>
      </div>
    );
  }
}

export default App;
