import IBalance from 'interface/IBalance';

export default function Expense(props: IBalance){
    const balanceStyle = props.balance > 0 ? "balancePositive" : "balanceNegative";    
    return (
        <div className={balanceStyle}>
            {props.name}: {props.balance.toFixed(2)}
        </div>
    );
}
