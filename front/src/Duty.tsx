import IDuty from 'IDuty';

export default function Expense(props: IDuty){
    return (
        <div className="Duty">
            {props.debtor} ---{'>'} {props.payer} ({props.amount.toFixed(2)}€)
        </div>
    );
}
