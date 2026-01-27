package Model.FileTable;

import Model.Exception.MyException;
import Model.SymTable.MyIDictionary;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class FileTable implements IFileTable {
    MyIDictionary<StringValue, BufferedReader> dictionary;
}
