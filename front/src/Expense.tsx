import React from 'react';
import IExpense from 'IExpense';

export default function Expense(props: IExpense){
        return (
            <div className="expense">
                <div className='name'>this.props.name</div>
                <div className='expense'>this.props.expense</div>
                <div className='description'>this.props.description</div>
                <div className='date'>this.props.date</div>
            </div>
        );
}
