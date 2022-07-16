import React from 'react';
import IExpense from 'IExpense';

export default function Expense(props: IExpense){
    const date = new Date(props.date);
    return (
        <div className="expense">
            <div className='name'>{props.person}</div>
            <div className='cost'>{props.cost.toFixed(2)} €</div>
            <div className='description'>{props.description}</div>
            <div className='date'>{date.toISOString()}</div>
        </div>
    );
}
