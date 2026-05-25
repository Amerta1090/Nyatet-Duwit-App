package com.nyatetduwit.domain.usecase.goal;

import com.nyatetduwit.domain.repository.GoalRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class GetActiveGoalsUseCase_Factory implements Factory<GetActiveGoalsUseCase> {
  private final Provider<GoalRepository> repositoryProvider;

  public GetActiveGoalsUseCase_Factory(Provider<GoalRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetActiveGoalsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetActiveGoalsUseCase_Factory create(Provider<GoalRepository> repositoryProvider) {
    return new GetActiveGoalsUseCase_Factory(repositoryProvider);
  }

  public static GetActiveGoalsUseCase newInstance(GoalRepository repository) {
    return new GetActiveGoalsUseCase(repository);
  }
}
