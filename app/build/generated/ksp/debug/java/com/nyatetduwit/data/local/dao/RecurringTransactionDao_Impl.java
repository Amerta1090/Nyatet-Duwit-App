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
import com.nyatetduwit.data.local.entity.RecurringTransactionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class RecurringTransactionDao_Impl implements RecurringTransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RecurringTransactionEntity> __insertionAdapterOfRecurringTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<RecurringTransactionEntity> __deletionAdapterOfRecurringTransactionEntity;

  private final EntityDeletionOrUpdateAdapter<RecurringTransactionEntity> __updateAdapterOfRecurringTransactionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeactivate;

  private final SharedSQLiteStatement __preparedStmtOfUpdateNextDue;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastProcessed;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSkippedDates;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RecurringTransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecurringTransactionEntity = new EntityInsertionAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recurring_transactions` (`id`,`template_transaction_id`,`frequency`,`start_date`,`end_date`,`next_due`,`is_active`,`last_processed`,`skipped_dates`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTemplateTransactionId());
        statement.bindString(3, entity.getFrequency());
        statement.bindLong(4, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndDate());
        }
        statement.bindLong(6, entity.getNextDue());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getLastProcessed() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getLastProcessed());
        }
        statement.bindString(9, entity.getSkippedDates());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfRecurringTransactionEntity = new EntityDeletionOrUpdateAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recurring_transactions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfRecurringTransactionEntity = new EntityDeletionOrUpdateAdapter<RecurringTransactionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recurring_transactions` SET `id` = ?,`template_transaction_id` = ?,`frequency` = ?,`start_date` = ?,`end_date` = ?,`next_due` = ?,`is_active` = ?,`last_processed` = ?,`skipped_dates` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecurringTransactionEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTemplateTransactionId());
        statement.bindString(3, entity.getFrequency());
        statement.bindLong(4, entity.getStartDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getEndDate());
        }
        statement.bindLong(6, entity.getNextDue());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getLastProcessed() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getLastProcessed());
        }
        statement.bindString(9, entity.getSkippedDates());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindString(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recurring_transactions SET is_active = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateNextDue = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recurring_transactions SET next_due = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastProcessed = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recurring_transactions SET last_processed = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSkippedDates = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recurring_transactions SET skipped_dates = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recurring_transactions";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final RecurringTransactionEntity recurring,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecurringTransactionEntity.insert(recurring);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<RecurringTransactionEntity> recurring,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfRecurringTransactionEntity.insert(recurring);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final RecurringTransactionEntity recurring,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecurringTransactionEntity.handle(recurring);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final RecurringTransactionEntity recurring,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRecurringTransactionEntity.handle(recurring);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deactivate(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivate.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeactivate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateNextDue(final String id, final long nextDue,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateNextDue.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, nextDue);
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
          __preparedStmtOfUpdateNextDue.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastProcessed(final String id, final long lastProcessed,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastProcessed.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, lastProcessed);
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
          __preparedStmtOfUpdateLastProcessed.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSkippedDates(final String id, final String skippedDates,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSkippedDates.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, skippedDates);
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
          __preparedStmtOfUpdateSkippedDates.release(_stmt);
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
  public Flow<List<RecurringTransactionEntity>> getAllActiveRecurring() {
    final String _sql = "SELECT * FROM recurring_transactions WHERE is_active = 1 ORDER BY next_due ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recurring_transactions"}, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_transaction_id");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfNextDue = CursorUtil.getColumnIndexOrThrow(_cursor, "next_due");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfLastProcessed = CursorUtil.getColumnIndexOrThrow(_cursor, "last_processed");
          final int _cursorIndexOfSkippedDates = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped_dates");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTemplateTransactionId;
            _tmpTemplateTransactionId = _cursor.getString(_cursorIndexOfTemplateTransactionId);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final long _tmpNextDue;
            _tmpNextDue = _cursor.getLong(_cursorIndexOfNextDue);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final Long _tmpLastProcessed;
            if (_cursor.isNull(_cursorIndexOfLastProcessed)) {
              _tmpLastProcessed = null;
            } else {
              _tmpLastProcessed = _cursor.getLong(_cursorIndexOfLastProcessed);
            }
            final String _tmpSkippedDates;
            _tmpSkippedDates = _cursor.getString(_cursorIndexOfSkippedDates);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpStartDate,_tmpEndDate,_tmpNextDue,_tmpIsActive,_tmpLastProcessed,_tmpSkippedDates,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<RecurringTransactionEntity>> getAllRecurring() {
    final String _sql = "SELECT * FROM recurring_transactions ORDER BY next_due ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recurring_transactions"}, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_transaction_id");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfNextDue = CursorUtil.getColumnIndexOrThrow(_cursor, "next_due");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfLastProcessed = CursorUtil.getColumnIndexOrThrow(_cursor, "last_processed");
          final int _cursorIndexOfSkippedDates = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped_dates");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTemplateTransactionId;
            _tmpTemplateTransactionId = _cursor.getString(_cursorIndexOfTemplateTransactionId);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final long _tmpNextDue;
            _tmpNextDue = _cursor.getLong(_cursorIndexOfNextDue);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final Long _tmpLastProcessed;
            if (_cursor.isNull(_cursorIndexOfLastProcessed)) {
              _tmpLastProcessed = null;
            } else {
              _tmpLastProcessed = _cursor.getLong(_cursorIndexOfLastProcessed);
            }
            final String _tmpSkippedDates;
            _tmpSkippedDates = _cursor.getString(_cursorIndexOfSkippedDates);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpStartDate,_tmpEndDate,_tmpNextDue,_tmpIsActive,_tmpLastProcessed,_tmpSkippedDates,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getRecurringById(final String id,
      final Continuation<? super RecurringTransactionEntity> $completion) {
    final String _sql = "SELECT * FROM recurring_transactions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<RecurringTransactionEntity>() {
      @Override
      @Nullable
      public RecurringTransactionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_transaction_id");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfNextDue = CursorUtil.getColumnIndexOrThrow(_cursor, "next_due");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfLastProcessed = CursorUtil.getColumnIndexOrThrow(_cursor, "last_processed");
          final int _cursorIndexOfSkippedDates = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped_dates");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final RecurringTransactionEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTemplateTransactionId;
            _tmpTemplateTransactionId = _cursor.getString(_cursorIndexOfTemplateTransactionId);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final long _tmpNextDue;
            _tmpNextDue = _cursor.getLong(_cursorIndexOfNextDue);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final Long _tmpLastProcessed;
            if (_cursor.isNull(_cursorIndexOfLastProcessed)) {
              _tmpLastProcessed = null;
            } else {
              _tmpLastProcessed = _cursor.getLong(_cursorIndexOfLastProcessed);
            }
            final String _tmpSkippedDates;
            _tmpSkippedDates = _cursor.getString(_cursorIndexOfSkippedDates);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new RecurringTransactionEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpStartDate,_tmpEndDate,_tmpNextDue,_tmpIsActive,_tmpLastProcessed,_tmpSkippedDates,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getDueRecurring(final long now,
      final Continuation<? super List<RecurringTransactionEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM recurring_transactions \n"
            + "        WHERE is_active = 1 AND next_due <= ?\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, now);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_transaction_id");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfNextDue = CursorUtil.getColumnIndexOrThrow(_cursor, "next_due");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfLastProcessed = CursorUtil.getColumnIndexOrThrow(_cursor, "last_processed");
          final int _cursorIndexOfSkippedDates = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped_dates");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTemplateTransactionId;
            _tmpTemplateTransactionId = _cursor.getString(_cursorIndexOfTemplateTransactionId);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final long _tmpNextDue;
            _tmpNextDue = _cursor.getLong(_cursorIndexOfNextDue);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final Long _tmpLastProcessed;
            if (_cursor.isNull(_cursorIndexOfLastProcessed)) {
              _tmpLastProcessed = null;
            } else {
              _tmpLastProcessed = _cursor.getLong(_cursorIndexOfLastProcessed);
            }
            final String _tmpSkippedDates;
            _tmpSkippedDates = _cursor.getString(_cursorIndexOfSkippedDates);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpStartDate,_tmpEndDate,_tmpNextDue,_tmpIsActive,_tmpLastProcessed,_tmpSkippedDates,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getAllRecurringSync(
      final Continuation<? super List<RecurringTransactionEntity>> $completion) {
    final String _sql = "SELECT * FROM recurring_transactions ORDER BY next_due ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<RecurringTransactionEntity>>() {
      @Override
      @NonNull
      public List<RecurringTransactionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTemplateTransactionId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_transaction_id");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfNextDue = CursorUtil.getColumnIndexOrThrow(_cursor, "next_due");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfLastProcessed = CursorUtil.getColumnIndexOrThrow(_cursor, "last_processed");
          final int _cursorIndexOfSkippedDates = CursorUtil.getColumnIndexOrThrow(_cursor, "skipped_dates");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<RecurringTransactionEntity> _result = new ArrayList<RecurringTransactionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecurringTransactionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTemplateTransactionId;
            _tmpTemplateTransactionId = _cursor.getString(_cursorIndexOfTemplateTransactionId);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final long _tmpNextDue;
            _tmpNextDue = _cursor.getLong(_cursorIndexOfNextDue);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final Long _tmpLastProcessed;
            if (_cursor.isNull(_cursorIndexOfLastProcessed)) {
              _tmpLastProcessed = null;
            } else {
              _tmpLastProcessed = _cursor.getLong(_cursorIndexOfLastProcessed);
            }
            final String _tmpSkippedDates;
            _tmpSkippedDates = _cursor.getString(_cursorIndexOfSkippedDates);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new RecurringTransactionEntity(_tmpId,_tmpTemplateTransactionId,_tmpFrequency,_tmpStartDate,_tmpEndDate,_tmpNextDue,_tmpIsActive,_tmpLastProcessed,_tmpSkippedDates,_tmpCreatedAt,_tmpUpdatedAt);
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
