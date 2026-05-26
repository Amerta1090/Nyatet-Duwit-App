package com.nyatetduwit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nyatetduwit.data.local.entity.TransactionSplitEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionSplitDao_Impl implements TransactionSplitDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TransactionSplitEntity> __insertionAdapterOfTransactionSplitEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionSplitEntity> __deletionAdapterOfTransactionSplitEntity;

  private final EntityDeletionOrUpdateAdapter<TransactionSplitEntity> __updateAdapterOfTransactionSplitEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByTransaction;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TransactionSplitDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransactionSplitEntity = new EntityInsertionAdapter<TransactionSplitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `transaction_splits` (`id`,`transaction_id`,`category_id`,`amount`,`notes`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionSplitEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTransactionId());
        statement.bindString(3, entity.getCategoryId());
        statement.bindLong(4, entity.getAmount());
        if (entity.getNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNotes());
        }
      }
    };
    this.__deletionAdapterOfTransactionSplitEntity = new EntityDeletionOrUpdateAdapter<TransactionSplitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `transaction_splits` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionSplitEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfTransactionSplitEntity = new EntityDeletionOrUpdateAdapter<TransactionSplitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transaction_splits` SET `id` = ?,`transaction_id` = ?,`category_id` = ?,`amount` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TransactionSplitEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTransactionId());
        statement.bindString(3, entity.getCategoryId());
        statement.bindLong(4, entity.getAmount());
        if (entity.getNotes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNotes());
        }
        statement.bindString(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByTransaction = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transaction_splits WHERE transaction_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM transaction_splits";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TransactionSplitEntity split,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransactionSplitEntity.insert(split);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<TransactionSplitEntity> splits,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTransactionSplitEntity.insert(splits);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final TransactionSplitEntity split,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTransactionSplitEntity.handle(split);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TransactionSplitEntity split,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransactionSplitEntity.handle(split);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByTransaction(final String transactionId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByTransaction.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, transactionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByTransaction.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<TransactionSplitEntity>> getSplitsByTransaction(final String transactionId) {
    final String _sql = "SELECT * FROM transaction_splits WHERE transaction_id = ? ORDER BY amount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, transactionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"transaction_splits"}, new Callable<List<TransactionSplitEntity>>() {
      @Override
      @NonNull
      public List<TransactionSplitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transaction_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<TransactionSplitEntity> _result = new ArrayList<TransactionSplitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionSplitEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTransactionId;
            _tmpTransactionId = _cursor.getString(_cursorIndexOfTransactionId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new TransactionSplitEntity(_tmpId,_tmpTransactionId,_tmpCategoryId,_tmpAmount,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSplitsByTransactionSync(final String transactionId,
      final Continuation<? super List<TransactionSplitEntity>> $completion) {
    final String _sql = "SELECT * FROM transaction_splits WHERE transaction_id = ? ORDER BY amount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, transactionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TransactionSplitEntity>>() {
      @Override
      @NonNull
      public List<TransactionSplitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transaction_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<TransactionSplitEntity> _result = new ArrayList<TransactionSplitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionSplitEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTransactionId;
            _tmpTransactionId = _cursor.getString(_cursorIndexOfTransactionId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new TransactionSplitEntity(_tmpId,_tmpTransactionId,_tmpCategoryId,_tmpAmount,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllSplitsSync(
      final Continuation<? super List<TransactionSplitEntity>> $completion) {
    final String _sql = "SELECT * FROM transaction_splits";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TransactionSplitEntity>>() {
      @Override
      @NonNull
      public List<TransactionSplitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "transaction_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<TransactionSplitEntity> _result = new ArrayList<TransactionSplitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TransactionSplitEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTransactionId;
            _tmpTransactionId = _cursor.getString(_cursorIndexOfTransactionId);
            final String _tmpCategoryId;
            _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new TransactionSplitEntity(_tmpId,_tmpTransactionId,_tmpCategoryId,_tmpAmount,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
