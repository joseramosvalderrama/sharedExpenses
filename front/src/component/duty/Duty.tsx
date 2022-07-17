import IDuty from 'interface/IDuty';

export default function Expense(props: IDuty){
    return (
        <div className="duty">
            {props.debtor} ---{'>'} {props.payer} ({props.amount.toFixed(2)}€)
        </div>
    );
}
