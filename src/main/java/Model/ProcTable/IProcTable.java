package Model.ProcTable;

import Model.IStmt.IStmt;
import Model.SymTable.MyIDictionary;
import Pair.Pair;
import java.util.List;

public interface IProcTable extends MyIDictionary<String, Pair<List<String>, IStmt>> {
}
