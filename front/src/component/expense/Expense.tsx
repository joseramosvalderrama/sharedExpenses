import IExpense from 'interface/IExpense';

interface IProps extends IExpense{
    onDelete: any
}

export default function Expense(props: IProps){
    const diffTime = new Date().getTime() - new Date(props.date).getTime();
    let difTimeField = '';
    if( diffTime < (1000 * 60)){
        difTimeField = 'Ahora mismo';
    }
    else if (diffTime < (1000 * 60 * 60)){
        difTimeField = "Hace " + Math.trunc(diffTime / (1000 * 60)) + " minutos";
    }
    else {
        difTimeField = "Hace " + Math.trunc(diffTime / (1000 * 60 * 60)) + " horas";
    }
    return (
        <div className="expense">
            <div className='expenseLeftItem'>{props.person}</div>
            <div className='expenseRightItem'>{props.cost.toFixed(2)} €</div>
            <div className='expenseLeftItem'>{props.description}</div>
            <div className='expenseRightItem'>{difTimeField}</div>
            <div className='expenseLeftItem'><button onClick={() => props.onDelete()}>Borrar</button></div>
        </div>
    );
}
