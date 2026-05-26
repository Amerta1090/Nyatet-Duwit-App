package com.nyatetduwit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nyatetduwit.data.local.entity.SplitBillEntity;
import com.nyatetduwit.data.local.entity.SplitBillPersonEntity;
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
public final class SplitBillDao_Impl implements SplitBillDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SplitBillEntity> __insertionAdapterOfSplitBillEntity;

  private final EntityInsertionAdapter<SplitBillPersonEntity> __insertionAdapterOfSplitBillPersonEntity;

  private final EntityDeletionOrUpdateAdapter<SplitBillEntity> __deletionAdapterOfSplitBillEntity;

  private final EntityDeletionOrUpdateAdapter<SplitBillEntity> __updateAdapterOfSplitBillEntity;

  private final EntityDeletionOrUpdateAdapter<SplitBillPersonEntity> __updateAdapterOfSplitBillPersonEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkSettled;

  private final SharedSQLiteStatement __preparedStmtOfMarkPersonPaid;

  private final SharedSQLiteStatement __preparedStmtOfDeletePersonsByBill;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllBills;

  public SplitBillDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSplitBillEntity = new EntityInsertionAdapter<SplitBillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `split_bills` (`id`,`title`,`total_amount`,`paid_by`,`date`,`notes`,`is_settled`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SplitBillEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getTotalAmount());
        statement.bindString(4, entity.getPaidBy());
        statement.bindLong(5, entity.getDate());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        final int _tmp = entity.isSettled() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
      }
    };
    this.__insertionAdapterOfSplitBillPersonEntity = new EntityInsertionAdapter<SplitBillPersonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `split_bill_persons` (`id`,`split_bill_id`,`name`,`amount`,`is_paid`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SplitBillPersonEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSplitBillId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getAmount());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfSplitBillEntity = new EntityDeletionOrUpdateAdapter<SplitBillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `split_bills` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SplitBillEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfSplitBillEntity = new EntityDeletionOrUpdateAdapter<SplitBillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `split_bills` SET `id` = ?,`title` = ?,`total_amount` = ?,`paid_by` = ?,`date` = ?,`notes` = ?,`is_settled` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SplitBillEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getTotalAmount());
        statement.bindString(4, entity.getPaidBy());
        statement.bindLong(5, entity.getDate());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        final int _tmp = entity.isSettled() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getId());
      }
    };
    this.__updateAdapterOfSplitBillPersonEntity = new EntityDeletionOrUpdateAdapter<SplitBillPersonEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `split_bill_persons` SET `id` = ?,`split_bill_id` = ?,`name` = ?,`amount` = ?,`is_paid` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SplitBillPersonEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSplitBillId());
        statement.bindString(3, entity.getName());
        statement.bindLong(4, entity.getAmount());
        final int _tmp = entity.isPaid() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getId());
      }
    };
    this.__preparedStmtOfMarkSettled = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE split_bills SET is_settled = 1, updated_at = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPersonPaid = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE split_bill_persons SET is_paid = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePersonsByBill = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM split_bill_persons WHERE split_bill_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllBills = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM split_bills";
        return _query;
      }
    };
  }

  @Override
  public Object insertBill(final SplitBillEntity bill,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSplitBillEntity.insert(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPerson(final SplitBillPersonEntity person,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSplitBillPersonEntity.insert(person);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPersons(final List<SplitBillPersonEntity> persons,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSplitBillPersonEntity.insert(persons);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAllBills(final List<SplitBillEntity> bills,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSplitBillEntity.insert(bills);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBill(final SplitBillEntity bill,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSplitBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBill(final SplitBillEntity bill,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSplitBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePerson(final SplitBillPersonEntity person,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSplitBillPersonEntity.handle(person);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markSettled(final String id, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSettled.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfMarkSettled.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markPersonPaid(final String personId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPersonPaid.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, personId);
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
          __preparedStmtOfMarkPersonPaid.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePersonsByBill(final String billId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePersonsByBill.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, billId);
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
          __preparedStmtOfDeletePersonsByBill.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllBills(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllBills.acquire();
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
          __preparedStmtOfDeleteAllBills.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SplitBillEntity>> getAllBills() {
    final String _sql = "SELECT * FROM split_bills ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"split_bills"}, new Callable<List<SplitBillEntity>>() {
      @Override
      @NonNull
      public List<SplitBillEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfPaidBy = CursorUtil.getColumnIndexOrThrow(_cursor, "paid_by");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<SplitBillEntity> _result = new ArrayList<SplitBillEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SplitBillEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getLong(_cursorIndexOfTotalAmount);
            final String _tmpPaidBy;
            _tmpPaidBy = _cursor.getString(_cursorIndexOfPaidBy);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new SplitBillEntity(_tmpId,_tmpTitle,_tmpTotalAmount,_tmpPaidBy,_tmpDate,_tmpNotes,_tmpIsSettled,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getBillById(final String id,
      final Continuation<? super SplitBillEntity> $completion) {
    final String _sql = "SELECT * FROM split_bills WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SplitBillEntity>() {
      @Override
      @Nullable
      public SplitBillEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfPaidBy = CursorUtil.getColumnIndexOrThrow(_cursor, "paid_by");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final SplitBillEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getLong(_cursorIndexOfTotalAmount);
            final String _tmpPaidBy;
            _tmpPaidBy = _cursor.getString(_cursorIndexOfPaidBy);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new SplitBillEntity(_tmpId,_tmpTitle,_tmpTotalAmount,_tmpPaidBy,_tmpDate,_tmpNotes,_tmpIsSettled,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
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
  public Flow<List<SplitBillPersonEntity>> getPersonsByBill(final String billId) {
    final String _sql = "SELECT * FROM split_bill_persons WHERE split_bill_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, billId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"split_bill_persons"}, new Callable<List<SplitBillPersonEntity>>() {
      @Override
      @NonNull
      public List<SplitBillPersonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSplitBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "split_bill_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "is_paid");
          final List<SplitBillPersonEntity> _result = new ArrayList<SplitBillPersonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SplitBillPersonEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSplitBillId;
            _tmpSplitBillId = _cursor.getString(_cursorIndexOfSplitBillId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            _item = new SplitBillPersonEntity(_tmpId,_tmpSplitBillId,_tmpName,_tmpAmount,_tmpIsPaid);
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
  public Object getPersonsByBillSync(final String billId,
      final Continuation<? super List<SplitBillPersonEntity>> $completion) {
    final String _sql = "SELECT * FROM split_bill_persons WHERE split_bill_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, billId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SplitBillPersonEntity>>() {
      @Override
      @NonNull
      public List<SplitBillPersonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSplitBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "split_bill_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfIsPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "is_paid");
          final List<SplitBillPersonEntity> _result = new ArrayList<SplitBillPersonEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SplitBillPersonEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSplitBillId;
            _tmpSplitBillId = _cursor.getString(_cursorIndexOfSplitBillId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final long _tmpAmount;
            _tmpAmount = _cursor.getLong(_cursorIndexOfAmount);
            final boolean _tmpIsPaid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaid);
            _tmpIsPaid = _tmp != 0;
            _item = new SplitBillPersonEntity(_tmpId,_tmpSplitBillId,_tmpName,_tmpAmount,_tmpIsPaid);
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
  public Object getAllBillsSync(final Continuation<? super List<SplitBillEntity>> $completion) {
    final String _sql = "SELECT * FROM split_bills ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SplitBillEntity>>() {
      @Override
      @NonNull
      public List<SplitBillEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfPaidBy = CursorUtil.getColumnIndexOrThrow(_cursor, "paid_by");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSettled = CursorUtil.getColumnIndexOrThrow(_cursor, "is_settled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<SplitBillEntity> _result = new ArrayList<SplitBillEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SplitBillEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getLong(_cursorIndexOfTotalAmount);
            final String _tmpPaidBy;
            _tmpPaidBy = _cursor.getString(_cursorIndexOfPaidBy);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSettled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSettled);
            _tmpIsSettled = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new SplitBillEntity(_tmpId,_tmpTitle,_tmpTotalAmount,_tmpPaidBy,_tmpDate,_tmpNotes,_tmpIsSettled,_tmpCreatedAt,_tmpUpdatedAt);
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
