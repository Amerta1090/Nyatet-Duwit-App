package com.nyatetduwit.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nyatetduwit.data.local.entity.ExchangeRateEntity;
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
public final class ExchangeRateDao_Impl implements ExchangeRateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ExchangeRateEntity> __insertionAdapterOfExchangeRateEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteRate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ExchangeRateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExchangeRateEntity = new EntityInsertionAdapter<ExchangeRateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `exchange_rates` (`currency_code`,`rate_to_base`,`updated_at`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ExchangeRateEntity entity) {
        statement.bindString(1, entity.getCurrencyCode());
        statement.bindDouble(2, entity.getRateToBase());
        statement.bindLong(3, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfDeleteRate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM exchange_rates WHERE currency_code = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM exchange_rates";
        return _query;
      }
    };
  }

  @Override
  public Object upsertRate(final ExchangeRateEntity rate,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExchangeRateEntity.insert(rate);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<ExchangeRateEntity> rates,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExchangeRateEntity.insert(rates);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRate(final String currencyCode,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteRate.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, currencyCode);
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
          __preparedStmtOfDeleteRate.release(_stmt);
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
  public Flow<List<ExchangeRateEntity>> getAllRates() {
    final String _sql = "SELECT * FROM exchange_rates ORDER BY currency_code ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exchange_rates"}, new Callable<List<ExchangeRateEntity>>() {
      @Override
      @NonNull
      public List<ExchangeRateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfRateToBase = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_to_base");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ExchangeRateEntity> _result = new ArrayList<ExchangeRateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ExchangeRateEntity _item;
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final double _tmpRateToBase;
            _tmpRateToBase = _cursor.getDouble(_cursorIndexOfRateToBase);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ExchangeRateEntity(_tmpCurrencyCode,_tmpRateToBase,_tmpUpdatedAt);
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
  public Object getRate(final String currencyCode,
      final Continuation<? super ExchangeRateEntity> $completion) {
    final String _sql = "SELECT * FROM exchange_rates WHERE currency_code = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, currencyCode);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ExchangeRateEntity>() {
      @Override
      @Nullable
      public ExchangeRateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfRateToBase = CursorUtil.getColumnIndexOrThrow(_cursor, "rate_to_base");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final ExchangeRateEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final double _tmpRateToBase;
            _tmpRateToBase = _cursor.getDouble(_cursorIndexOfRateToBase);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ExchangeRateEntity(_tmpCurrencyCode,_tmpRateToBase,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
